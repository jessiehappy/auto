package auto.qr.service.user;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import auto.datamodel.BasicJson;
import auto.datamodel.dao.DUser;
import auto.datamodel.dao.DAuth;

/**
 * 大B 经销商 -server层
 */


public interface IDUserService {
	/**
	 * 条件查询
	 * @param username
	 * @return 
	 */
	DUser getDUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<DUser> getDUsers(List<String> usernames);
	/**
	 * 
	 * @param id
	 * @return
	 */
	DUser getDUser(long id);
	
	/**
	 * 创建
	 * @param telephone
	 * @param password
	 * @return
	 */
	DUser createDUser(String telephone,String password);
	/**
	 * 更新
	 * @param username
	 * @param Info
	 * @return
	 */
	DUser updateInfo(String username,Map<String, Object> Info);
	/**
	 * 注册
	 * @param deviceId
	 * @param telephone
	 * @param phoneNum
	 * @param picCode
	 * @param password
	 * @return
	 */
	BasicJson register(String deviceId, String telephone, String phoneNum,
			String picCode, String password);
	/**
	 * 重置密码
	 * @param username
	 * @param oldPS
	 * @param newPS
	 * @return
	 */
	Boolean reset(String username,String oldPS, String newPS);
	/**
	 * 更改手机号码
	 * @param username
	 * @param telephone
	 * @param deviceId
	 * @param phoneNum
	 * @return
	 */
	boolean editPhone(String username, String telephone, String deviceId,
			String phoneNum);
	/**
	 * 更改性别
	 * @param username
	 * @param gender
	 * @return
	 */
	boolean editGender(String username, String gender);
	/**
	 * 更改昵称
	 * @param username
	 * @param nickName
	 * @return
	 */
	boolean editNickname(String username, String nickName);
	/**
	 * 更改头像地址
	 * @param username
	 * @param favicon
	 * @return
	 */
	boolean editFavicon(String username, String favicon);
	
	/**
	 * 创建  dealer_auth
	 * @param username   	 //账户
	 * @param telephone		 //公司电话
	 * @param address   	//公司地址
	 * @param company		//公司名称
	 * @param businessLicence//营业执照
	 * @param longitude		//经度
	 * @param latitude		//纬度
	 * @return
	 */
	DAuth createDAuth(String username, String telephone, String address,
			String company, String businessLicence, BigDecimal longitude,
			BigDecimal latitude);
	/**
	 * 更新  dealer_auth
	 * @param username
	 * @param info
	 * @return
	 */
	DAuth updateDAuthInfo(String username, Map<String, Object> info);
	
	
	
    
}
