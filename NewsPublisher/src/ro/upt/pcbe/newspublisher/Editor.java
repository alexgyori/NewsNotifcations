package ro.upt.pcbe.newspublisher;
import javax.jms.*;
import javax.naming.*;

import java.io.*;
import java.util.Properties;

public class Editor  {
		
	/* Run the Chat client */
	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		
		NewsPublisher publisher = new NewsPublisher("NewsNotifications", "guest", "guest");
		try {
			ReadNotificationsSubscriber sub = new ReadNotificationsSubscriber("ReadNotifications", "guest", "guest");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		publisher.start();
		while(true)
		{
			
		}
		//publisher.join();		
	}
}



