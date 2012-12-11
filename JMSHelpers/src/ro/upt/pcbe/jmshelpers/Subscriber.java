package ro.upt.pcbe.jmshelpers;

import javax.jms.*;
import javax.naming.*;
import java.io.*;
import java.util.Properties;

public abstract class Subscriber implements javax.jms.MessageListener {
	private TopicSession subSession;
	private TopicConnection connection;
	private Topic topic;
	private TopicSubscriber subscriber;

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

		refreshSubscriber(subSession,topic);

		set(subSession, connection, topic);

		// Start the JMS connection; allows messages to be delivered
		connection.start();

	}


	private void refreshSubscriber(TopicSession session, Topic topic)
	{
		// Create a JMS subscriber
		
		try {
			if(this.subscriber!=null)
			{
				this.subscriber.close();
			}
			this.subscriber = session.createSubscriber(topic, getSelectorString(),true);
			// Set a JMS message listener
			this.subscriber.setMessageListener(this);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void set(TopicSession subSession, TopicConnection connection, Topic topic) {
		// TODO Auto-generated method stub
		this.connection = connection;
		this.subSession = subSession;
		this.topic = topic;
	}

	/* Receive message from topic subscriber */
	public synchronized void onMessage(Message message) {

		String text = message.toString();

		System.out.println(text);
	}

	//call this to apply the possible change in the desired filtering
	public void updateFilter()
	{
		refreshSubscriber(this.subSession, this.topic);
	}
	
	abstract protected String getSelectorString();

	/* Close the JMS connection */
	public void close() throws JMSException {
		connection.close();
	}
}




