package ro.upt.pcbe.newssubscriber;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;

import ro.upt.pcbe.jmshelpers.DoSub;
import ro.upt.pcbe.jmshelpers.Publisher;
import ro.upt.pcbe.jmshelpers.Subscriber;

public class TopicsClient extends Thread {

	private CopyOnWriteArraySet<DoSub> topicsSet;
	private int myID;
	
	public void run(){
		
		myID = System.identityHashCode(this);
		topicsSet = new CopyOnWriteArraySet<DoSub>();
		
		// Context for requesting the topics
		TopicRequestPublisher pub = null;
		
		try {
			pub = new TopicRequestPublisher("DomainsNotifications", "guest", "guest");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Context for receiving the topics
		//WARNING: This should be ok BEFORE sending the request!
		TopicReceiveSubscriber sub = null;
		
		try {
			sub = new TopicReceiveSubscriber("DomainsNotifications", "guest", "guest");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sub.setSelectorString("ID='"+String.valueOf(myID)+"'"+" AND isRequest = 'false' ");
		
		//Send request
		pub.sendRequest(String.valueOf(myID));
		System.out.println("Sent topic request....");
		//Get Topics
		topicsSet = sub.getTopics();
	}
	
	public CopyOnWriteArraySet<DoSub> getTopics(){
		return topicsSet;
	}
	
}

class TopicRequestPublisher extends Publisher{

	public TopicRequestPublisher(String topicName, String username,
			String password) throws NamingException, JMSException {
		super(topicName, username, password);
		// TODO Auto-generated constructor stub
	}
	
	public void sendRequest(String ID){
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("ID", ID);
		hm.put("isRequest", "true");
		try {
			this.publish(hm);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

class TopicReceiveSubscriber extends Subscriber{

	private String selectorString;
	private CopyOnWriteArraySet<DoSub> topics;
	
	public TopicReceiveSubscriber(String topicName, String username,
			String password) throws Exception {
		super(topicName, username, password);
		// TODO Auto-generated constructor stub
	}

	public void setSelectorString(String s){
		selectorString = s;
	}
	
	@Override
	protected String getSelectorString() {
		return selectorString;
	}
	
	/* Receive message from topic subscriber */
	public synchronized void onMessage(Message message) {

		HashSet<DoSub> hs;
		
		if (message instanceof ObjectMessage){
			try {
				hs = (HashSet) ((ObjectMessage)message).getObject();
				System.out.println(hs);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//TODO: check hs and put in topics
		
		
	}
	
	public CopyOnWriteArraySet<DoSub> getTopics(){
		return this.topics;
	}
	
}