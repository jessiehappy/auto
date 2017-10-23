package auto.qr.service.user;

import java.util.List;
import java.util.Map;

import auto.datamodel.BasicJson;
import auto.datamodel.dao.User;


public interface IUserService {
	/**
	 * 条件查询
	 * @param username
	 * @return User
	 */
	User getUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<User> getUsers(List<String> usernames);
	/**
	 * 
	 * @param id
	 * @return
	 */
	User getUser(long id);
	/**
	 * 创建
	 * @param telephone
	 * @param wechatId
	 * @param wechatName
	 * @param wechatFavicon
	 * @return
	 */
	User createUser(String telephone,String wechatId,String wechatName,String wechatFavicon);
	/**
	 * 更新
	 * @param username
	 * @param Info
	 * @return
	 */
	User updateInfo(String username,Map<String, Object> Info);
	/**
	 * 发送验证短信
	 * @param telephone
	 */
	boolean sendPhoneNum(String telephone,String deviceId);
	/**
	 * 发送验证图片
	 * @param deviceId
	 * @return
	 */
	String sendGraphCode(String deviceId);
	/**
	 * 短信验证
	 * @param deviceId
	 * @param phoneNum
	 * @return
	 */
	boolean verifyPhoneNum(String deviceId,String phoneNum);
	/**
	 * 图片验证
	 * @param deviceId
	 * @param picCode
	 * @return
	 */
	boolean verifyPicCode(String deviceId,String picCode);
	/**
	 * 登录
	 * @param deviceId
	 * @param telephone
	 * @param phoneNum
	 * @param picCode
	 * @return
	 */
	BasicJson login(String deviceId, String telephone, String phoneNum,
			String picCode,String wechatId,String wechatName,String wechatFavicon);
	
    
}
