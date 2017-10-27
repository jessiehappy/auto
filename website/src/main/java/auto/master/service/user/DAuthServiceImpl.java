package auto.master.service.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.datamodel.AuthStatus;
import auto.datamodel.dao.DAuth;
import auto.master.dao.user.IDAuthDao;
import auto.util.CommonUtils;

@Service("masterDAuthService")
public class DAuthServiceImpl implements IDAuthService {
    
    @Autowired
    private IDAuthDao DAuthDao;
    @Autowired
    private IDUserService duserService;


	@Override
	public DAuth updateInfo(String username, Map<String, Object> info) {
		DAuth user=DAuthDao.getDAuth(username);
		if(user!=null){
			CommonUtils.updateInfo(user, info);
			DAuthDao.update(user);
		}
		return user;
	}

	@Override
	public DAuth createDAuth(String username,String telephone,String address,String company,String businessLicence ,
			BigDecimal longitude,BigDecimal latitude) {
		DAuth user=new DAuth();
		
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
