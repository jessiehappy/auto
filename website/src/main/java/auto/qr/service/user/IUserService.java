package auto.qr.service.user;

/**
 * 短信发送验证、图片发送验证
 */
public interface IUserService {

	/**
	 * 发送验证短信
	 * @param telephone
	 */
	boolean sendPhoneNum(String telephone,String openId);
	/**
	 * 发送验证图片
	 * @param deviceId
	 * @return
	 */
	String sendGraphCode(String openId);
	/**
	 * 短信验证
	 * @param deviceId
	 * @param phoneNum
	 * @return
	 */
	boolean verifyPhoneNum(String openId,String phoneNum);
	/**
	 * 图片验证
	 * @param deviceId
	 * @param picCode
	 * @return
	 */
	boolean verifyPicCode(String openId,String picCode);
	
    
}
