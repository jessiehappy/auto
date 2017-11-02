package auto.master.service.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.datamodel.AuthStatus;
import auto.datamodel.dao.ProxyAuth;
import auto.master.dao.user.IProxyAuthDao;
import auto.util.CommonUtils;

@Service("masterPAuthService")
public class ProxyAuthServiceImpl implements IProxyAuthService {
    
    @Autowired
    private IProxyAuthDao pauthDao;
    
    @Autowired
    private IProxyUserService puserService;
    

	@Override
	public ProxyAuth updateInfo(String username, Map<String, Object> info) {
		ProxyAuth user=pauthDao.getPAuth(username);
		if(user!=null){
			CommonUtils.updateInfo(user, info);
			pauthDao.update(user);
		}
		return user;
	}

	@Override
	public ProxyAuth createPAuth(String openId, String username,String telephone, String realName, String idNo,
			String frontImg, String qualification,String longitude, String latitude) {
		
		ProxyAuth user=new ProxyAuth();
		
		user.setOpenId(openId);
		user.setUsername(username);
	    user.setRealName(realName);
	    user.setIdNo(idNo);
	    user.setFrontImg(frontImg);
	    user.setQualification(qualification);
	    user.setUsername(username);
	    user.setCreateTime(new Date());
	    
		user.setStatus(AuthStatus.AUDIT.ordinal());//审核中
		user.setLongitude(new BigDecimal(longitude));//经度
		user.setLatitude(new BigDecimal(latitude));//纬度
		pauthDao.createPAuth(user);
		//更新 proxy_user表 status 认证状态
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("status", AuthStatus.AUDIT.ordinal());
		puserService.updateInfo(username, info);
		
		return user;		
	}
}
