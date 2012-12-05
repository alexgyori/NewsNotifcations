package ro.upt.pcbe.newspublisher;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.jms.JMSException;
import javax.jms.TopicPublisher;
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
		while(true)
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
	
	public Map<String, String> makeHm()
	{
		Map<String,String> hm = new HashMap<String, String>();
		hm.put("domain", "Sport");
		hm.put("subdomain", "Tenis");
		hm.put("creationDate", new Date().toString());
		hm.put("lastModificationDate", new Date().toString());
		hm.put("link", "http://hotnews.ro");
		return hm;
	}
	
}
