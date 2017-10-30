package auto.master.service.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.qr.dao.user.IProxyUserDao;
import auto.datamodel.AuthStatus;
import auto.datamodel.dao.ProxyAuth;
import auto.master.dao.user.IProxyAuthDao;
import auto.util.CommonUtils;

@Service("masterPAuthService")
public class ProxyAuthServiceImpl implements IProxyAuthService {
    
    @Autowired
    private IProxyAuthDao pauthDao;
    
    @Autowired
    private IProxyUserDao puserdao;
    
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
	public ProxyAuth createPAuth(String telephone, String realName, String idNo,
			String frontImg, String qualification) {
		ProxyAuth user=new ProxyAuth();
		
		String username=puserdao.getPUser(telephone).getUsername();
		
	    user.setRealName(realName);
	    user.setIdNo(idNo);
	    user.setFrontImg(frontImg);
	    user.setQualification(qualification);
	    user.setUsername(username);
	    user.setCreateTime(new Date());
	    
		user.setStatus(AuthStatus.AUDIT.ordinal());//审核中
		
		pauthDao.createPAuth(user);
		//更新 proxy_user表 status 认证状态
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("status", AuthStatus.AUDIT.ordinal());
		puserService.updateInfo(username, info);
		
		return user;		
	}
}
