package auto.master.service.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.datamodel.dao.User;
import auto.master.dao.user.IUserDao;
import auto.util.CommonUtils;
import auto.util.StringUtils;

@Service("masterUserService")
public class UserServiceImpl implements IUserService {
    
    @Autowired
    private IUserDao userDao;

	@Override
	public User updateInfo(String username, Map<String, Object> info) {
		User user=userDao.getUser(username);
		if(user!=null){
			CommonUtils.updateInfo(user, info);
			userDao.update(user);
		}
		return user;
	}

	@Override
	public User createUser(String telephone, String wechatId,
			String wechatName, String wechatFavicon) {
		User user=new User();
		
		Date date=new Date();
		user.setCreateTime(date);
		user.setTelephone(telephone);
		user.setWechatId(wechatId);
		user.setWechatName(wechatName);
		user.setWechatFavicon(wechatFavicon);
		
		userDao.createUser(user);
		
		return user;		
	}
}
