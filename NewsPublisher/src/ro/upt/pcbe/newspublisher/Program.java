package ro.upt.pcbe.newspublisher;
import javax.jms.*;
import javax.naming.*;

import java.io.*;
import java.util.Properties;

public class Program implements javax.jms.MessageListener {
	
	
	private TopicSession publisherSession;
	private TopicSession subscriberSession;
	private TopicPublisher publisher;
	private TopicConnection connection;
	private String username;
	
	private ReadNotificationsSubscriber readCounter;

	/* Constructor. Establish JMS publisher and subscriber */
	public Program(String topicName, String username, String password)
			throws Exception {
		this.username=username;
		
		init(topicName, username, password);

	}

	private void init(String topicName, String username, String password) throws NamingException, JMSException
	{
		Properties env = new Properties();
		// ... specify the JNDI properties specific to the vendor

		InitialContext jndi = new InitialContext(env);

		// Look up a JMS connection factory

		TopicConnectionFactory conFactory = (TopicConnectionFactory) jndi .lookup("TopicConnectionFactory");

		// Create a JMS connection
		TopicConnection connection = conFactory.createTopicConnection(username, password);

		// Create two JMS session objects
		TopicSession pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

		// Look up a JMS topic
		Topic chatTopic = (Topic) jndi.lookup(topicName);

		// Create a JMS publisher and subscriber
		TopicPublisher publisher = pubSession.createPublisher(chatTopic);
		TopicSubscriber subscriber = subSession.createSubscriber(chatTopic);

		// Set a JMS message listener
		subscriber.setMessageListener(this);

		// Intialize the Chat application
		set(connection, pubSession, subSession, publisher, username);

		// Start the JMS connection; allows messages to be delivered
		connection.start();
	}
	/* Initialize the instance variables */
	public void set(TopicConnection con, TopicSession pubSess,
			TopicSession subSess, TopicPublisher pub, String username) {
		this.connection = con;
		this.publisherSession = pubSess;
		this.subscriberSession = subSess;
		this.publisher = pub;
		this.username = username;
	}

	/* Receive message from topic subscriber */
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);
		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}
	}

	/* Create and send message using topic publisher */
	protected void writeMessage(String text) throws JMSException {
		TextMessage message = publisherSession.createTextMessage();
		ObjectMessage obm = publisherSession.createObjectMessage();
		
		message.setText(username + " : " + text);
		publisher.publish(message);
	}

	/* Close the JMS connection */
	public void close() throws JMSException {
		connection.close();
	}

	/* Run the Chat client */
	public static void main(String[] args) {
		try {
			if (args.length != 3) System.out.println("Topic or username missing");
			// args[0]=topicName; args[1]=username; args[2]=password 
			Program chat = new Program("testTopic", "guest", "guest");
			// Read from command line
			BufferedReader commandLine = new java.io.BufferedReader( new InputStreamReader(System.in)); // Loop until the word "exit" is typed
			while (true) 
			{ 
				String s = commandLine.readLine(); 
				if (s.equalsIgnoreCase("exit")) 
				{ 
					chat.close(); // close down connection 
					System.exit(0);// exit program 
				} 
				else 
					chat.writeMessage(s);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}



