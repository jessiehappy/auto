package auto.qr.service.wx;

public interface WXService {
	/**
	 * 根据code获取openId
	 * @param code
	 * @return
	 */
	String getOpenId(String code) throws Exception;

}
