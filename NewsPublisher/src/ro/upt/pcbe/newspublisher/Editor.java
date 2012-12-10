package ro.upt.pcbe.newspublisher;
import javax.jms.*;
import javax.naming.*;

import java.io.*;
import java.util.Properties;

public class Editor  {
		
	/* Run the Chat client */
	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		
		NewsPublisher publisher = new NewsPublisher("NewsNotifications", "guest", "guest");
		publisher.start();
		//publisher.join();		
	}
}



