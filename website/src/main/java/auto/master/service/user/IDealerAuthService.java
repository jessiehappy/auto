package auto.master.service.user;

import java.math.BigDecimal;
import java.util.Map;

import auto.datamodel.dao.DealerAuth;

/**
 *供应商认证表   dealer_auth  ----操作
 */
public interface IDealerAuthService {
	/**
	 * 更新
	 * @param username
	 * @param info
	 * @return
	 */
	DealerAuth updateInfo(String username,Map<String,Object> info);    
	
    /**
     * 创建
     * @param username  账号
     * @param telephone 公司电话
     * @param address   公司地址
     * @param company   公司名称
     * @param businessLicence  营业执照
     * @param longitude  经度
     * @param latitude   纬度
     * @return
     */
	DealerAuth createDAuth(String username,String telephone,String address,String company,String businessLicence ,
			BigDecimal longitude,BigDecimal latitude);
	

}
