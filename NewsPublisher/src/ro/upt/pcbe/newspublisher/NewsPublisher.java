package ro.upt.pcbe.newspublisher;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.jms.JMSException;
import javax.naming.NamingException;

import ro.upt.pcbe.jmshelpers.Publisher;

public class NewsPublisher extends Publisher{


	public NewsPublisher(String topicName, String username, String password) throws NamingException, JMSException
	{
		super(topicName, username, password);
	}

	@Override
	public void run()
	{
		Random r = new Random();
		for(int i = 0; i<1000;i++)
		{
			try {
				this.publish(this.makeHm());
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				Thread.sleep(r.nextInt(10000));
				System.out.println("having a break");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private int i;
	public Map<String, String> makeHm()
	{
		Map<String,String> hm = new HashMap<String, String>();
		String date = new Date().toString();
		if(i==1)
		{
			i=0;
			
			hm.put("domain", "Sport");
			hm.put("subdomain", "Tenis");
			hm.put("creationDate", date);
			hm.put("newsId", "1");
			hm.put("newsType", "new");
			hm.put("lastModificationDate", new Date().toString());
			hm.put("link", "http://tenisnews.ro");
			System.out.println("Sending Sport/Tenis news:"+date);
		}
		else
		{
			i=1;
			hm.put("domain", "Sport");
			hm.put("subdomain", "Gigi");
			hm.put("creationDate", date);
			hm.put("newsId", "2");
			hm.put("newsType", "new");
			hm.put("lastModificationDate", new Date().toString());
			hm.put("link", "http://giginews.ro");
			System.out.println("Sending Sport/Gigi news:"+date);
		}
		return hm;
	}

}
