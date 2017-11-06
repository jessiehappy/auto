package auto.qr.dao.user;

import java.util.List;

import auto.dao.IReadonlyDao;
import auto.datamodel.dao.ProxyUser;;

public interface IProxyUserDao extends IReadonlyDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	ProxyUser getPUser(long id);
	/**
	 * 
	 * @param username
	 * @return
	 */
	ProxyUser getPUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<ProxyUser> getPUsers(List<String> usernames);
	ProxyUser getUserByToken(String token);
	
    

}
