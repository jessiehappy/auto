package auto.master.service.user;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.util.UserUtils;
import auto.datamodel.Status;
import auto.datamodel.dao.CUser;
import auto.master.dao.user.ICUserDao;
import auto.util.CommonUtils;

@Service("masterCUserService")
public class CUserServiceImpl implements ICUserService {
    
    @Autowired
    private ICUserDao cuserDao;

	@Override
	public CUser updateInfo(String username, Map<String, Object> info) {
		CUser user=cuserDao.getCUser(username);
		if(user!=null){
			CommonUtils.updateInfo(user, info);
			cuserDao.update(user);
		}
		return user;
	}

	@Override
	public CUser createCUser(String telephone, String wechatId,
			String wechatName, String wechatFavicon,String openId) {
		CUser user=new CUser();
		
		user.setUsername(UserUtils.generateUsername());
		
		Date date=new Date();
		user.setCreateTime(date);
		
		user.setTelephone(telephone);
		user.setWechatId(wechatId);
		user.setWechatName(wechatName);
		user.setWechatFavicon(wechatFavicon);
		user.setOpenId(openId);
		user.setDataStatus(Status.NORMAL.ordinal());
		
		cuserDao.createCUser(user);
		
		return user;		
	}
}
