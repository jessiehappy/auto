package auto.qr.dao.user;

import java.util.List;

import auto.dao.IReadonlyDao;
import auto.datamodel.dao.DealerUser;

public interface IDealerUserDao extends IReadonlyDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	DealerUser getDUser(long id);
	/**
	 * 
	 * @param username
	 * @return
	 */
	DealerUser getDUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<DealerUser> getDUsers(List<String> usernames);
	
    

}
