package ro.upt.pcbe.jmshelpers;

import javax.jms.*;
import javax.naming.*;
import java.io.*;
import java.util.Properties;

public class Subscriber implements javax.jms.MessageListener {
	private TopicSession subSession;
	private TopicConnection connection;

	/* Constructor. Establish JMS publisher and subscriber */
	public Subscriber(String topicName, String username, String password)
			throws Exception {
		// Obtain a JNDI connection
		Properties env = new Properties();
		// ... specify the JNDI properties specific to the vendor

		InitialContext jndi = new InitialContext(env);

		// Look up a JMS connection factory

		TopicConnectionFactory conFactory = (TopicConnectionFactory) jndi .lookup("TopicConnectionFactory");

		// Create a JMS connection
		TopicConnection connection = conFactory.createTopicConnection(username, password);

		// Create JMS session object
		TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

		// Look up a JMS topic
		Topic topic = (Topic) jndi.lookup(topicName);

		// Create a JMS subscriber
		TopicSubscriber subscriber = subSession.createSubscriber(topic);

		// Set a JMS message listener
		subscriber.setMessageListener(this);

		set(subSession, connection);
		
		// Start the JMS connection; allows messages to be delivered
		connection.start();

	}

	private void set(TopicSession subSession, TopicConnection connection) {
		// TODO Auto-generated method stub
		this.connection = connection;
		this.subSession = subSession;
	}

	/* Receive message from topic subscriber */
	public void onMessage(Message message) {
		Message msg = message;
		String text = msg.toString();
		
		System.out.println(text);
	}

	
	/* Close the JMS connection */
	public void close() throws JMSException {
		connection.close();
	}
}


