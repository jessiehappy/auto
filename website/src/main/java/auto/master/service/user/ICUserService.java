package auto.master.service.user;

import java.util.Map;

import auto.datamodel.dao.CUser;

public interface ICUserService {
	/**
	 * 更新
	 * @param username
	 * @param info
	 * @return
	 */
	CUser updateInfo(String username,Map<String,Object> info);    
	/**
	 * 创建
	 * @param telephone
	 * @param wechatId
	 * @param wechatName
	 * @param wechatFavicon
	 * @return
	 */
	CUser createCUser(String telephone,String wechatId,String wechatName, String wechatFavicon,String openId);
	
}
