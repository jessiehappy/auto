package auto.qr.dao.user;

import java.util.List;

import auto.dao.IReadonlyDao;
import auto.datamodel.dao.CustomUser;

public interface ICustomUserDao extends IReadonlyDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	CustomUser getCUser(long id);
	/**
	 * 
	 * @param username
	 * @return
	 */
	CustomUser getCUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<CustomUser> getCUsers(List<String> usernames);
	
    

}
