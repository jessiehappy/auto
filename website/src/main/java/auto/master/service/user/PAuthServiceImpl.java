package auto.master.service.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.qr.dao.user.IPUserDao;
import auto.datamodel.AuthStatus;
import auto.datamodel.dao.PAuth;
import auto.master.dao.user.IPAuthDao;
import auto.util.CommonUtils;

@Service("masterPAuthService")
public class PAuthServiceImpl implements IPAuthService {
    
    @Autowired
    private IPAuthDao pauthDao;
    
    @Autowired
    private IPUserDao puserdao;
    
    @Autowired
    private IPUserService puserService;

	@Override
	public PAuth updateInfo(String username, Map<String, Object> info) {
		PAuth user=pauthDao.getPAuth(username);
		if(user!=null){
			CommonUtils.updateInfo(user, info);
			pauthDao.update(user);
		}
		return user;
	}

	@Override
	public PAuth createPAuth(String telephone, String realName, String idNo,
			String frontImg, String qualification) {
		PAuth user=new PAuth();
		
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
