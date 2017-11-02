package auto.qr.service.user;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import auto.datamodel.BasicJson;
import auto.datamodel.dao.DealerUser;
import auto.datamodel.dao.DealerAuth;

/**
 * 大B 经销商 -server层
 */


public interface IDealerUserService {
	/**
	 * 条件查询
	 * @param username
	 * @return 
	 */
	DealerUser getDUser(String username);
	/**
	 * 
	 * @param usernames
	 * @return
	 */
	List<DealerUser> getDUsers(List<String> usernames);
	/**
	 * 
	 * @param id
	 * @return
	 */
	DealerUser getDUser(long id);
	
	/**
	 * 创建
	 * @param telephone
	 * @param password
	 * @return
	 */
	DealerUser createDUser(String telephone,String password);
	/**
	 * 更新
	 * @param username
	 * @param Info
	 * @return
	 */
	DealerUser updateInfo(String username,Map<String, Object> Info);
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
	DealerAuth createDAuth(String username, String telephone, String address,
			String company, String businessLicence, BigDecimal longitude,
			BigDecimal latitude);
	/**
	 * 更新  dealer_auth
	 * @param username
	 * @param info
	 * @return
	 */
	DealerAuth updateDAuthInfo(String username, Map<String, Object> info);
	/**
	 * 更新审核状态（dealer_auth/dealer_user表 ： 字段status）
	 * @param username
	 * @param info
	 */
	void updateDAuthStatus(String username, Map<String, Object> info);
	
	
	
    
}
