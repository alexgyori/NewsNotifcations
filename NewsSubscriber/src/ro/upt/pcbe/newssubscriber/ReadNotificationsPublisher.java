package ro.upt.pcbe.newssubscriber;

import javax.jms.JMSException;
import javax.naming.NamingException;

import ro.upt.pcbe.jmshelpers.*;

public class ReadNotificationsPublisher extends Publisher {

	public ReadNotificationsPublisher(String topicName, String username,
			String password) throws NamingException, JMSException {
		super(topicName, username, password);
		// TODO Auto-generated constructor stub
	}

}
