package auto.qr.dao.user;

import java.util.List;

import auto.dao.IReadonlyDao;
import auto.datamodel.dao.ProxyAuth;;

public interface IProxyAuthDao extends IReadonlyDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	ProxyAuth getPAuth(long id);
	/**
	 * 
	 * @param username
	 * @return
	 */
	ProxyAuth getPAuth(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<ProxyAuth> getPAuths(List<String> usernames);
	
    

}
