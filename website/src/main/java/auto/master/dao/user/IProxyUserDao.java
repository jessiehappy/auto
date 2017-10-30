package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.ProxyUser;

public interface IProxyUserDao extends IDao{
	
	ProxyUser getPUser(String username);
	
	List<ProxyUser> getPUsers(Collection<String> usernames);

	ProxyUser createPUser(ProxyUser user);
    
}
