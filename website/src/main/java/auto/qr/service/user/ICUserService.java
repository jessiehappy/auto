package auto.qr.service.user;

import java.util.List;
import java.util.Map;

import auto.datamodel.BasicJson;
import auto.datamodel.dao.CUser;


@SuppressWarnings("unused")
public interface ICUserService {
	/**
	 * 条件查询
	 * @param username
	 * @return 
	 */
	CUser getCUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<CUser> getCUsers(List<String> usernames);
	/**
	 * 
	 * @param id
	 * @return
	 */
	CUser getCUser(long id);
	/**
	 * 创建
	 * @param telephone
	 * @param wechatId
	 * @param wechatName
	 * @param wechatFavicon
	 * @return
	 */
	CUser createCUser(String telephone,String wechatId,String wechatName,String wechatFavicon,String openId);
	/**
	 * 更新
	 * @param username
	 * @param Info
	 * @return
	 */
	CUser updateInfo(String username,Map<String, Object> Info);
    
}
