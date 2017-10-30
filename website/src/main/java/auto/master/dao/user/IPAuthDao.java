package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.PAuth;

public interface IPAuthDao extends IDao{
	
	PAuth getPAuth(String username);
	
	List<PAuth> getPAuths(Collection<String> usernames);

	PAuth createPAuth(PAuth user);
    
}
