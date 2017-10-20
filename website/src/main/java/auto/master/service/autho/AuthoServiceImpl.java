package auto.master.service.autho;


import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import auto.util.CodeUtil;
import auto.util.Md5Utils;
import auto.util.RedisManager;
import auto.util.StringUtils;

@Service
@CommonsLog
public class AuthoServiceImpl implements IAuthoService{

	private static final Integer OUT_LOGIN_DAYS = 7;
	private static final Integer OUT_LOGIN_MSECONDS = OUT_LOGIN_DAYS * 24 * 3600;
//	private static final Integer OUT_CODE_MSECONDS = 3600 * 1000;
	
	private static final Integer IMAGE_TOKEN_EXPIERD = 60;
	
	/**
	 * 获取图片上传的授权
	 */
	@Override
	public String getImageToken(String ip) {
		if(StringUtils.isEmpty(ip)) return null;
		String code = ip + CodeUtil.getCode();
		String token = Md5Utils.sign(code.getBytes());
		RedisManager.set(token.getBytes(), ip.getBytes(), IMAGE_TOKEN_EXPIERD);
		return token;
	}

	/**
	 * 通过文件上传授权码获取文件上传IP
	 */
	@Override
	public String getUploadIp(String accessToken) {
		if(StringUtils.isEmpty(accessToken)) return null;
		byte[] ip = null;
		if(RedisManager.get(accessToken.getBytes()) != null) {
			ip = RedisManager.get(accessToken.getBytes());
		} 
		RedisManager.del(accessToken.getBytes());
		if(ip == null) return null;
		return new String(ip);
	}
}
