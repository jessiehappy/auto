package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.CUser;

public interface ICUserDao extends IDao{
	
	CUser getCUser(String username);
	
	List<CUser> getCUsers(Collection<String> usernames);

	CUser createCUser(CUser user);
    
}
