package ro.upt.pcbe.jmshelpers;

import java.io.Serializable;

public class DoSub implements Serializable{

	
	public String domain;
	public String subdomain;
	
	public DoSub(String domain, String subdomain) {
		// TODO Auto-generated constructor stub
		this.domain = domain;
		this.subdomain = subdomain;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result
				+ ((subdomain == null) ? 0 : subdomain.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoSub other = (DoSub) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (subdomain == null) {
			if (other.subdomain != null)
				return false;
		} else if (!subdomain.equals(other.subdomain))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DoSub [domain=" + domain + ", subdomain=" + subdomain + "]";
	}
	
	
}
