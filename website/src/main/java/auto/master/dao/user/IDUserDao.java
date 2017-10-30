package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.DUser;

public interface IDUserDao extends IDao{
	
	DUser getDUser(String username);
	
	List<DUser> getDUsers(Collection<String> usernames);

	DUser createDUser(DUser user);
    
}
