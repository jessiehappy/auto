package auto.master.service.user;

import java.util.Map;

import auto.datamodel.dao.DUser;

public interface IDUserService {
	/**
	 * 更新
	 * @param username
	 * @param info
	 * @return
	 */
	DUser updateInfo(String username,Map<String,Object> info);    
	/**
	 * 创建
	 * @param telephone
	 * @param password
	 * @return
	 */
	DUser createDUser(String telephone,String password);
	
}
