package auto.qr.service.user;

import java.util.List;
import java.util.Map;

import auto.datamodel.BasicJson;
import auto.datamodel.dao.ProxyUser;
import auto.datamodel.dao.ProxyAuth;
import auto.datamodel.service.ProxyUserResult;

/**
 * 小B 代理商- server层
 */
@SuppressWarnings("unused")
public interface IProxyUserService {
	/**
	 * 条件查询
	 * @param username
	 * @return 
	 */
	ProxyUser getPUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<ProxyUser> getPUsers(List<String> usernames);
	/**
	 * 
	 * @param id
	 * @return
	 */
	ProxyUser getPUser(long id);
	/**
	 * 创建
	 * @param telephone
	 * @param wechatId
	 * @param wechatName
	 * @param wechatFavicon
	 * @return
	 */
	ProxyUser createPUser(String telephone,String wechatId,String wechatName,String wechatFavicon,String openId);
	/**
	 * 更新
	 * @param username
	 * @param Info
	 * @return
	 */
	ProxyUser updateInfo(String username,Map<String, Object> Info);
	
	/**
	 * 鉴定用户是否已经认证,返回认证状态值
	 * @param telephone
	 * @return
	 */
	int identifyPUser(String telephone);
	/**
	 * 切换账号
	 * @param telephone
	 * @param openId
	 * @return
	 */
	ProxyUser switchPUser(String telephone, String openId);
	/**
	 * 提交   代理人认证  申请(新建认证表)
	 * @param telephone
	 * @param realName
	 * @param idNo
	 * @param frontImg
	 * @param qualification
	 */
	ProxyAuth createPAuth(String telephone, String realName, String idNo,
			String frontImg, String qualification);
	/**
	 * 审核  代理人认证   （更新认证表）
	 * @param username
	 * @param info
	 */
	ProxyAuth updatePAuthInfo(String username, Map<String, Object> info);
	
    
}
