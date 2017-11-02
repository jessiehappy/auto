package auto.master.dao.user;

import java.util.Map;

import auto.dao.IDao;
import auto.datamodel.dao.ProxyAuth;

public interface IProxyAuthDao extends IDao{
	
	/**
	 * 创建
	 * @param user
	 * @return
	 */
	ProxyAuth createPAuth(ProxyAuth user);
	
	/**
	 * 条件查询
	 */
	ProxyAuth getPAuth(String username);
	
}
