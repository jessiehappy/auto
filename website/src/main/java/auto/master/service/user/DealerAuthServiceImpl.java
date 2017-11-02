package auto.master.service.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.datamodel.AuthStatus;
import auto.datamodel.dao.DealerAuth;
import auto.master.dao.user.IDealerAuthDao;
import auto.util.CommonUtils;

@Service("masterDAuthService")
public class DealerAuthServiceImpl implements IDealerAuthService {
    
    @Autowired
    private IDealerAuthDao DAuthDao;
    @Autowired
    private IDealerUserService duserService;


	@Override
	public DealerAuth updateInfo(String username, Map<String, Object> info) {
		DealerAuth user=DAuthDao.getDAuth(username);
		if(user!=null){
			CommonUtils.updateInfo(user, info);
			DAuthDao.update(user);
		}
		return user;
	}

	@Override
	public DealerAuth createDAuth(String username,String telephone,String address,String company,String businessLicence ,
			BigDecimal longitude,BigDecimal latitude) {
		DealerAuth user=new DealerAuth();
		
		user.setUsername(username);
		user.setTelephone(telephone);
		user.setAddress(address);
		user.setBusinessLicence(businessLicence);
		user.setCompany(company);
		user.setLatitude(latitude);
		user.setLongitude(longitude);
		user.setStatus(AuthStatus.AUDIT.ordinal());  //审核中
		//创建认证
		DAuthDao.createDAuth(user);
		
		//更新DUser表中的认证状态
		Map<String, Object>info=new HashMap<String, Object>();
		info.put("status", AuthStatus.AUDIT.ordinal());
		duserService.updateInfo(username, info);

		return user;		
	}
}
