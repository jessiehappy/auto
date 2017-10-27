package auto.master.service.user;

import java.util.Map;

import auto.datamodel.dao.PAuth;

/**
 *代理人认证表   proxy_auth  ----操作
 */
public interface IPAuthService {
	/**
	 * 更新
	 * @param username
	 * @param info
	 * @return
	 */
	PAuth updateInfo(String username,Map<String,Object> info);    
	
    /**
     * 创建
     * @param telephone
     * @param realName
     * @param idNo
     * @param frontImg
     * @param qualification
     * @return
     */
	PAuth createPAuth(String telephone, String realName, String idNo,
			String frontImg, String qualification);
	

}
