package auto.qr.dao.user;

import java.util.List;

import auto.dao.IReadonlyDao;
import auto.datamodel.dao.User;

public interface IUserDao extends IReadonlyDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	User getUser(long id);
	/**
	 * 
	 * @param username
	 * @return
	 */
	User getUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<User> getUsers(List<String> usernames);
	
    

}
