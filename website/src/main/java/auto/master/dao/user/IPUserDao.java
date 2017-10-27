package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.PUser;

public interface IPUserDao extends IDao{
	
	PUser getPUser(String username);
	
	List<PUser> getPUsers(Collection<String> usernames);

	PUser createPUser(PUser user);
    
}
