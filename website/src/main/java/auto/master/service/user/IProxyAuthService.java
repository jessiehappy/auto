package auto.master.service.user;

import java.util.Map;

import auto.datamodel.dao.ProxyAuth;

/**
 *代理人认证表   proxy_auth  ----操作
 */
public interface IProxyAuthService {
	/**
	 * 更新
	 * @param username
	 * @param info
	 * @return
	 */
	ProxyAuth updateInfo(String username,Map<String,Object> info);    
	
    /**
     * 创建
     * @param openId
     * @param username
     * @param telephone
     * @param realName
     * @param idNo
     * @param frontImg
     * @param qualification
     * @param latitude2 
     * @return
     */
	ProxyAuth createPAuth(String openId, String username, String telephone, String realName, String idNo,
			String frontImg, String qualification, String longitude, String latitude);
	

}
