package ro.upt.pcbe.newspublisher;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

import ro.upt.pcbe.jmshelpers.Subscriber;

public class DomainSubscriber extends Subscriber {

	public DomainSubscriber(String topicName, String username, String password)
			throws Exception {
		super(topicName, username, password);
	}
	
	@Override
	public void onMessage(Message msg)
	{
		try {
			DomainPublisher.getInstance().publishAllDomainsTo(msg.getStringProperty("ID"));
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected String getSelectorString() {

		return "ID IS NOT NULL AND isRequest='true'";
	}

}
