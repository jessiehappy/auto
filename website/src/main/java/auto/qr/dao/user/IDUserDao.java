package auto.qr.dao.user;

import java.util.List;

import auto.dao.IReadonlyDao;
import auto.datamodel.dao.DUser;

public interface IDUserDao extends IReadonlyDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	DUser getDUser(long id);
	/**
	 * 
	 * @param username
	 * @return
	 */
	DUser getDUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<DUser> getDUsers(List<String> usernames);
	
    

}
