package auto.qr.dao.user;

import java.util.List;

import auto.dao.IReadonlyDao;
import auto.datamodel.dao.PUser;;

public interface IPUserDao extends IReadonlyDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	PUser getPUser(long id);
	/**
	 * 
	 * @param username
	 * @return
	 */
	PUser getPUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<PUser> getPUsers(List<String> usernames);
	
    

}
