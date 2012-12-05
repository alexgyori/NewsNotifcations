package ro.upt.pcbe.newspublisher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.naming.NamingException;

import ro.upt.pcbe.jmshelpers.Publisher;

public class DomainPublisher extends Publisher{

	public DomainPublisher(String topicName, String username, String password)
			throws NamingException, JMSException {
		super(topicName, username, password);		
	}
	private Map<String, List<String>> domainsToSubdomains;
	
	public void publishAllDomainsTo(String id) throws JMSException
	{
		for(String domain : this.domainsToSubdomains.keySet())
		{
			for(String sub : this.domainsToSubdomains.get(domain))
			{
				this.publish(this.makeIdentifiedDomainResponse(id, domain, sub));
			}
		}
	}
	
	public Map<String,String> makeIdentifiedDomainResponse(String id, String domain, String subdomain)
	{
		Map<String,String> hm = makeDomainMessage(domain, subdomain);
		hm.put("isResponse", "true");
		hm.put("id", id);
		return hm;
		
	}
	
	public void addDomain(String domain, String subdomain) throws JMSException
	{
		if(this.domainsToSubdomains.containsKey(domain))
		{
			this.domainsToSubdomains.get(domain).add(subdomain);
		}
		else
		{
			this.domainsToSubdomains.put(domain, Arrays.asList(subdomain));
		}
		this.publish(this.makeDomainMessage(domain, subdomain));
	}

	private Map<String, String> makeDomainMessage(String domain,
			String subdomain) {
		Map<String,String> hm = new HashMap<String,String>();
		hm.put("domain", domain);
		hm.put("subdomain", subdomain);
		return hm;
	}

	

}
