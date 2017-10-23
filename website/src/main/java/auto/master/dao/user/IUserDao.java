package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.User;

public interface IUserDao extends IDao{
	
	User getUser(String username);
	
	List<User> getUsers(Collection<String> usernames);

	User createUser(User user);
    
}
