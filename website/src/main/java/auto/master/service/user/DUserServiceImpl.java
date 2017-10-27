package auto.master.service.user;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.util.UserUtils;
import auto.datamodel.AuthStatus;
import auto.datamodel.GenderType;
import auto.datamodel.Status;
import auto.datamodel.dao.DUser;
import auto.master.dao.user.IDUserDao;
import auto.util.CommonUtils;

@Service("masterDUserService")
public class DUserServiceImpl implements IDUserService {
    
    @Autowired
    private IDUserDao duserDao;

	@Override
	public DUser updateInfo(String username, Map<String, Object> info) {
		DUser user=duserDao.getDUser(username);
		if(user!=null){
			CommonUtils.updateInfo(user, info);
			duserDao.update(user);
		}
		return user;
	}

	@Override
	public DUser createDUser(String telephone, String password) {
		DUser user=new DUser();
		
		user.setUsername(UserUtils.generateUsername());
		
		Date date=new Date();
		user.setCreateTime(date);
		
		user.setTelephone(telephone);
		
		user.setPassword(password);
		
		//默认值
		user.setStatus(AuthStatus.UNKNOWN.ordinal());
		user.setDataStatus(Status.NORMAL.ordinal());
		user.setGender(GenderType.UNKNOWN.ordinal());
		user.setNickName(UserUtils.generateUsername());
		
		duserDao.createDUser(user);
		
		return user;		
	}
}
