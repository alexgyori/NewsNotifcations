package ro.upt.pcbe.newspublisher;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.jms.JMSException;
import javax.naming.NamingException;

import ro.upt.pcbe.jmshelpers.Publisher;

public class DomainPublisher extends Publisher{

	public static DomainPublisher getInstance() throws NamingException, JMSException
	{
		if(instance == null)
		{
			synchronized(DomainPublisher.class)
			{
				if(instance==null)
					instance = new DomainPublisher("DomainsNotifications","guest", "guest");
			}
		}
		return instance;
	}
	
	private static volatile DomainPublisher instance;
	
	private DomainPublisher(String topicName, String username, String password)
			throws NamingException, JMSException {
		super(topicName, username, password);
		this.domainsToSubdomains=new ConcurrentHashMap<String,Set<String>>();
		this.putSubdomainToHashMap("Sport", "Fotbal");
		this.putSubdomainToHashMap("Sport", "Basket");
		this.putSubdomainToHashMap("Sport", "Judo");
		this.putSubdomainToHashMap("Sport", "Diverse");
		this.putSubdomainToHashMap("Politica", "Inutile");
		this.putSubdomainToHashMap("Tehnologie", "Cutting-edge");
		this.putSubdomainToHashMap("Tehnologie", "Amuzante");
		
	}
	private ConcurrentHashMap<String, Set<String>> domainsToSubdomains;
	
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
		putSubdomainToHashMap(domain, subdomain);
		this.publish(this.makeDomainMessage(domain, subdomain));
	}
	//TODO: Review this to make sure it works;
	//this method is called from multiple threads so it needs to be threadsafe
	private void putSubdomainToHashMap(String domain, String subdomain) {		
		Set<String> set = new HashSet<String>();
		set.add(subdomain);	
		set = Collections.synchronizedSet(set);
		//the map is concurrent, so it will return non-null if the key already exists in the map
		//the set is also concurrent so adding an item to it should be safe
		if(this.domainsToSubdomains.putIfAbsent(domain, set)!=null)
		{
			
				this.domainsToSubdomains.get(domain).add(subdomain);			
		}
		
	}

	private Map<String, String> makeDomainMessage(String domain,
			String subdomain) {
		Map<String,String> hm = new HashMap<String,String>();
		hm.put("domain", domain);
		hm.put("subdomain", subdomain);
		return hm;
	}

	

}