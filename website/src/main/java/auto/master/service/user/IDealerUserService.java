package auto.master.service.user;

import java.util.Map;

import auto.datamodel.dao.DealerUser;

public interface IDealerUserService {
	/**
	 * 更新
	 * @param username
	 * @param info
	 * @return
	 */
	DealerUser updateInfo(String username,Map<String,Object> info);    
	/**
	 * 创建
	 * @param telephone
	 * @param password
	 * @return
	 */
	DealerUser createDUser(String telephone,String password);
	
}
