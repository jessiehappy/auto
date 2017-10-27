package auto.master.service.user;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.util.UserUtils;
import auto.datamodel.Status;
import auto.datamodel.dao.PUser;
import auto.master.dao.user.IPUserDao;
import auto.util.CommonUtils;

@Service("masterPUserService")
public class PUserServiceImpl implements IPUserService {
    
    @Autowired
    private IPUserDao puserDao;

	@Override
	public PUser updateInfo(String username, Map<String, Object> info) {
		PUser user=puserDao.getPUser(username);
		if(user!=null){
			CommonUtils.updateInfo(user, info);
			puserDao.update(user);
		}
		return user;
	}

	@Override
	public PUser createPUser(String telephone, String wechatId,
			String wechatName, String wechatFavicon,String openId) {
		PUser user=new PUser();
		
		user.setUsername(UserUtils.generateUsername());
		
		Date date=new Date();
		user.setCreateTime(date);
		
		user.setTelephone(telephone);
		user.setWechatId(wechatId);
		user.setWechatName(wechatName);
		user.setWechatFavicon(wechatFavicon);
		user.setOpenId(openId);
		user.setStatus(Status.NORMAL.ordinal());
		
		puserDao.createPUser(user);
		
		return user;		
	}
}
