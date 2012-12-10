package ro.upt.pcbe.newssubscriber;
import javax.jms.JMSException;

import ro.upt.pcbe.jmshelpers.Subscriber;




public class Reader {

	/**
	 * @param args
	 * @throws JMSException 
	 */
	public static void main(String[] args) throws JMSException {
		// TODO Auto-generated method stub
		NewsSubscriber sub=null;
		try {
			sub = new NewsSubscriber("NewsNotifications", "guest", "guest");
			
			sub.addDomain(new DoSub("Sport","Gigi"));
			Thread.sleep(10000);
			System.out.println("Filtering to receive only Gigi news");
			sub.updateFilter();
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
