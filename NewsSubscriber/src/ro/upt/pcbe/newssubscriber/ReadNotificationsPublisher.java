package ro.upt.pcbe.newssubscriber;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.naming.NamingException;

import ro.upt.pcbe.jmshelpers.*;

public class ReadNotificationsPublisher extends Publisher {

	public ReadNotificationsPublisher(String topicName, String username,
			String password) throws NamingException, JMSException {
		super(topicName, username, password);
		// TODO Auto-generated constructor stub
	}

	public synchronized void sendReadMessage(String newsId)
	{
		Map<String,String> hm = new HashMap<String,String>();
		hm.put("newsId", newsId);
		try {
			this.publish(hm);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
