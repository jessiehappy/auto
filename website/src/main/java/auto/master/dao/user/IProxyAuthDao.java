package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.ProxyAuth;

public interface IProxyAuthDao extends IDao{
	
	ProxyAuth getPAuth(String username);
	
	List<ProxyAuth> getPAuths(Collection<String> usernames);

	ProxyAuth createPAuth(ProxyAuth user);
    
}
