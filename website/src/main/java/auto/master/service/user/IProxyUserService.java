package auto.master.service.user;

import java.util.Map;

import auto.datamodel.dao.ProxyUser;

public interface IProxyUserService {
	/**
	 * 更新
	 * @param username
	 * @param info
	 * @return
	 */
	ProxyUser updateInfo(String username,Map<String,Object> info);    
	/**
	 * 创建
	 * @param telephone
	 * @param wechatId
	 * @param wechatName
	 * @param wechatFavicon
	 * @return
	 */
	ProxyUser createPUser(String telephone,String wechatId,String wechatName, String wechatFavicon,String openId);
	
}
