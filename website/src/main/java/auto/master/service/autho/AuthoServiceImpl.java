package auto.master.service.autho;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;
import auto.datamodel.UserType;
import auto.datamodel.dao.CustomUser;
import auto.datamodel.dao.DealerUser;
import auto.datamodel.dao.ProxyUser;
import auto.qr.dao.user.ICustomUserDao;
import auto.qr.dao.user.IDealerUserDao;
import auto.qr.dao.user.IProxyUserDao;
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
	
	@Autowired
	private ICustomUserDao customUserDao;
	
	@Autowired
	private IProxyUserDao proxyUserDao;
	
	@Autowired
	private IDealerUserDao dealerUserDao;
	
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

	/**
	 * type is user type @see {@link UserType}
	 * autho username must be like xinxilu_xxxx_xxx
	 * grant return one code
	 * grant custom, proxy, dealer user
	 */
	@Override
	public String grant(String username, UserType type) {
		try {
			if(username == null || type == null) return null;
			String code = CodeUtil.getCode();
			String token = null;
			if(type.equals(UserType.CONSUMER)) {
				CustomUser user = customUserDao.getCUser(username);
				token = user.getToken() + "_" + new Date().getTime();
			}else if(type.equals(UserType.PROXY)) {
				ProxyUser user = proxyUserDao.getPUser(username);
				token = user.getToken() + "_" + new Date().getTime();
			}else if(type.equals(UserType.DEALER)) {
				DealerUser user= dealerUserDao.getDUser(username);
				token = user.getToken() + "_" + new Date().getTime();
			}else {
				return null;
			}
			if(token != null && RedisManager.set(code.getBytes(), token.getBytes(), OUT_LOGIN_MSECONDS)){
				return code;
			}
		}catch (Exception e) {
			log.error("user code grant failed, " + e);
			return null;
		}
		return null;
	}

	@Override
	public void cancleGrant(String code) {
		// TODO Auto-generated method stub
		try {
			RedisManager.del(code.getBytes());
		}catch (Exception e) {
			log.error("cancle grant failed" + e);
		}
	}

	@Override
	public String getTokenByCode(String code) {
		if(code == null) return null;
		String token = null;
		try {
			byte[] values = RedisManager.get(code.getBytes());
			if(values!=null) {
				token = new String(values);
				String ss[] = token.split("_");
				if(new Date().getTime() - Long.parseLong(ss[1]) > OUT_LOGIN_MSECONDS) {
					RedisManager.del(code.getBytes());
					return null;
				}
				token = ss[0];
			}
		}catch (Exception e) {
			log.error("get token by code error", e);
			return null;
		}
		return token;
	}

	@Override
	public String freshCode(String code) {
		if(code == null) return null;
		try {
			byte[] values = RedisManager.get(code.getBytes());
			if(values == null) {
				return null;
			}
			String token = new String(values);
			String[] ss = token.split("_");
			Long now = new Date().getTime();
			//token 过期，需要重新授权
			if(now - Long.parseLong(ss[1]) > OUT_LOGIN_MSECONDS) {
				RedisManager.del(code.getBytes());
				return null;
			}
			//token 不过期，重新分配授权码
			String newToken = ss[0] + "_" + now;
			RedisManager.del(code.getBytes());
			String newCode = CodeUtil.getCode();
			if(RedisManager.set(newCode.getBytes(), newToken.getBytes(),OUT_LOGIN_MSECONDS )) {
				return newCode;
			}
		}catch (Exception e) {
			log.error("fresh code failed, " + e);
			return null;
		}
		return null;
	}

	@Override
	public CustomUser getCustomUserByCode(String code) {
		if(code==null || "".equals(code)) return null;
		try {
			String token = getTokenByCode(code);
			CustomUser user = customUserDao.getUserByToken(token);
			return user;
		}catch (Exception e) {
			log.error("get user by code failed " + e);
		}
		return null;
	}

	@Override
	public ProxyUser getProxyUserByCode(String code) {
		if(code==null || "".equals(code)) return null;
		try {
			String token = getTokenByCode(code);
			ProxyUser user = proxyUserDao.getUserByToken(token);
			return user;
		}catch (Exception e) {
			log.error("get user by code failed " + e);
		}
		return null;
	}

	@Override
	public DealerUser getDealerUserByCode(String code) {
		if(code==null || "".equals(code)) return null;
		try {
			String token = getTokenByCode(code);
			DealerUser user = dealerUserDao.getUserByToken(token);
			return user;
		}catch (Exception e) {
			log.error("get user by code failed " + e);
		}
		return null;
	}
}
