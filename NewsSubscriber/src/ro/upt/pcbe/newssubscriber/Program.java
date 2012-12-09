package ro.upt.pcbe.newssubscriber;
import javax.jms.JMSException;

import ro.upt.pcbe.jmshelpers.Subscriber;




public class Program {

	/**
	 * @param args
	 * @throws JMSException 
	 */
	public static void main(String[] args) throws JMSException {
		// TODO Auto-generated method stub
		Subscriber sub=null;
		try {
			sub = new Subscriber("NewsNotifications", "guest", "guest");
			
			while(true)
			{
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			sub.close();
		}
		
	}

}
