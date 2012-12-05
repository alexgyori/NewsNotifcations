package ro.upt.pcbe.jmshelpers;

import java.util.Map;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.jms.Message;

abstract public class Publisher extends Thread{
	private TopicSession topicSession;
	private TopicPublisher topicPublisher;
	private TopicConnection topicConection;
	
	public Publisher(String topicName, String username, String password) throws NamingException, JMSException
	{
		init(topicName, username, password);
	}
	
	private void init(String topicName, String username, String password) throws NamingException, JMSException
	{
		//Make proerties
		Properties env = new Properties();
		
		// ... specify the JNDI properties specific to the vendor
		InitialContext jndi = new InitialContext(env);

		// Look up a JMS connection factory

		TopicConnectionFactory conFactory = (TopicConnectionFactory) jndi .lookup("TopicConnectionFactory");

		// Create a JMS connection
		this.topicConection = conFactory.createTopicConnection(username, password);

		// Create two JMS session objects
		this.topicSession = this.topicConection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		
		// Look up a JMS topic
		Topic chatTopic = (Topic) jndi.lookup(topicName);

		// Create a JMS publisher and subscriber
		topicPublisher = this.topicSession.createPublisher(chatTopic);
	}
	
	public void publish(Map<String,String> hm) throws JMSException
	{
		Message msg = this.topicSession.createMessage();
		for(String key : hm.keySet())
		{
			msg.setStringProperty(key, hm.get(key));
		}
		topicPublisher.publish(msg);
	}
}
