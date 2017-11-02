package auto.master.service.user;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.util.UserUtils;
import auto.datamodel.AuthStatus;
import auto.datamodel.GenderType;
import auto.datamodel.Status;
import auto.datamodel.dao.DealerUser;
import auto.master.dao.user.IDealerUserDao;
import auto.util.CommonUtils;

@Service("masterDUserService")
public class DealerUserServiceImpl implements IDealerUserService {
    
    @Autowired
    private IDealerUserDao duserDao;

	@Override
	public DealerUser updateInfo(String username, Map<String, Object> info) {
		DealerUser user=duserDao.getDUser(username);
		if(user!=null){
			CommonUtils.updateInfo(user, info);
			duserDao.update(user);
		}
		return user;
	}

	@Override
	public DealerUser createDUser(String telephone, String password) {
		DealerUser user=new DealerUser();
		
		user.setUsername(UserUtils.generateUsername());
		
		Date date=new Date();
		user.setCreateTime(date);
		
		user.setTelephone(telephone);
		
		user.setPassword(password);
		
		//默认值
		user.setStatus(AuthStatus.UNKNOWN.ordinal());//默认认证状态
		user.setDataStatus(Status.NORMAL.ordinal());//默认数据状态
		user.setGender(GenderType.UNKNOWN.ordinal());//默认性别
		user.setNickName(UserUtils.generateUsername());//默认昵称
		user.setFavicon(DealerUser.DEFAULT_FAVICON);//默认头像
		
		duserDao.createDUser(user);
		
		return user;		
	}
}
