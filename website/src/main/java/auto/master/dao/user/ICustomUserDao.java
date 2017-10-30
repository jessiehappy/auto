package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.CustomUser;

public interface ICustomUserDao extends IDao{
	
	CustomUser getCUser(String username);
	
	List<CustomUser> getCUsers(Collection<String> usernames);

	CustomUser createCUser(CustomUser user);
    
}
