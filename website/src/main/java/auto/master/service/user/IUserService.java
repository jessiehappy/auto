package auto.master.service.user;

import java.util.Map;

import auto.datamodel.dao.User;

public interface IUserService {
	/**
	 * 更新
	 * @param username
	 * @param info
	 * @return
	 */
	User updateInfo(String username,Map<String,Object> info);    
	/**
	 * 创建
	 * @param telephone
	 * @param wechatId
	 * @param wechatName
	 * @param wechatFavicon
	 * @return
	 */
	User createUser(String telephone,String wechatId,String wechatName, String wechatFavicon);
	
}
