package auto.master.service.user;

import java.util.Map;

import auto.datamodel.dao.CustomUser;

public interface ICustomUserService {
	/**
	 * 更新
	 * @param username
	 * @param info
	 * @return
	 */
	CustomUser updateInfo(String username,Map<String,Object> info);    
	/**
	 * 创建
	 * @param telephone
	 * @param wechatId
	 * @param wechatName
	 * @param wechatFavicon
	 * @return
	 */
	CustomUser createCUser(String telephone,String wechatId,String wechatName, String wechatFavicon,String openId);
	
}
