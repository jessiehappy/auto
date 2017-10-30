package auto.qr.service.user;

import java.util.List;
import java.util.Map;

import auto.datamodel.BasicJson;
import auto.datamodel.dao.CustomUser;


@SuppressWarnings("unused")
public interface ICustomUserService {
	/**
	 * 条件查询
	 * @param username
	 * @return 
	 */
	CustomUser getCUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<CustomUser> getCUsers(List<String> usernames);
	/**
	 * 
	 * @param id
	 * @return
	 */
	CustomUser getCUser(long id);
	/**
	 * 创建
	 * @param telephone
	 * @param wechatId
	 * @param wechatName
	 * @param wechatFavicon
	 * @return
	 */
	CustomUser createCUser(String telephone,String wechatId,String wechatName,String wechatFavicon,String openId);
	/**
	 * 更新
	 * @param username
	 * @param Info
	 * @return
	 */
	CustomUser updateInfo(String username,Map<String, Object> Info);
    
}
