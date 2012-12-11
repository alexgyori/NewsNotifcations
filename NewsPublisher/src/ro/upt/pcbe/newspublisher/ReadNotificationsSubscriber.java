package ro.upt.pcbe.newspublisher;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.JMSException;
import javax.jms.Message;

import ro.upt.pcbe.jmshelpers.Subscriber;



public class ReadNotificationsSubscriber  extends Subscriber{

	public ReadNotificationsSubscriber(String topicName, String username, String password) throws Exception
	{
		super(topicName, username,password);
	}
	
	private Map<String, Integer> newsToNumberOfReads = new ConcurrentHashMap<String, Integer>();
	@Override
	//synchronized on the whole method because it needs to be atomic
	//because of jms this method gets called concurrently
	public void onMessage(Message arg0) {
		try {
			String newsId = arg0.getStringProperty("newsId");
			//Check if already received notifications for the current news id
			//if not add a new entry with number of occurances 1.
			Integer count = 0;
			synchronized(this)
			{
				System.out.println("user read news with id="+newsId);
				if(this.newsToNumberOfReads.containsKey(newsId))
				{
					count = this.newsToNumberOfReads.get(newsId);					
				}							
				this.newsToNumberOfReads.put(newsId, count+1);
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	protected String getSelectorString() {
		// TODO Auto-generated method stub
		return null;
	}

}
