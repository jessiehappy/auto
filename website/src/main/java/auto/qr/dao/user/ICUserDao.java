package auto.qr.dao.user;

import java.util.List;

import auto.dao.IReadonlyDao;
import auto.datamodel.dao.CUser;

public interface ICUserDao extends IReadonlyDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	CUser getCUser(long id);
	/**
	 * 
	 * @param username
	 * @return
	 */
	CUser getCUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<CUser> getCUsers(List<String> usernames);
	
    

}
