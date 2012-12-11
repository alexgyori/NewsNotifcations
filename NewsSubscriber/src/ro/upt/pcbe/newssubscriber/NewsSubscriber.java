package ro.upt.pcbe.newssubscriber;
import java.util.HashSet;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;

import ro.upt.pcbe.jmshelpers.Subscriber;


public class NewsSubscriber extends Subscriber {

	private Set<DoSub> interestedDomains;

	public NewsSubscriber(String topicName, String username, String password)
			throws Exception {
		super(topicName, username, password);
		interestedDomains = new HashSet<DoSub>();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getSelectorString() {
		String selector="";
		int i=0;
		if(interestedDomains != null)
		for(DoSub d : interestedDomains)
		{
			if((d.domain != null )&&(!d.domain.isEmpty())&&(d.subdomain != null )&&(!d.subdomain.isEmpty()))
			{
				i++;
				selector+="( domain='"+d.domain+"' AND subdomain='"+d.subdomain+"' )";
				if(i<interestedDomains.size())
					selector+=" OR ";
			}
		}
		System.out.println("New selector -"+selector+"-");
		return selector;
	}

	@Override
	public synchronized void onMessage(Message message) {

		try {
			System.out.println(message.getStringProperty("link")+":"+message.getStringProperty("creationDate"));
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addDomain(DoSub d) {
		interestedDomains.add(d);
	}

	public void removeDomain(DoSub d)
	{
		interestedDomains.remove(d);
	}

}
