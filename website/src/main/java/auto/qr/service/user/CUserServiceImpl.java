package auto.qr.service.user;

import java.util.List;
import java.util.Map;
import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auto.datamodel.dao.CUser;
import auto.qr.dao.user.ICUserDao;
import auto.util.StringUtils;

@CommonsLog
@Service
@Transactional
public class CUserServiceImpl implements ICUserService {

    @Autowired
    private ICUserDao cuserDao;
    
    @Qualifier(value = "masterCUserService")
    @Autowired
    private auto.master.service.user.ICUserService cuserService;

	@Override
	public CUser getCUser(String username) {
		return StringUtils.isEmpty(username)?null:cuserDao.getCUser(username);
	}

	@Override
	public List<CUser> getCUsers(List<String> usernames) {
		return cuserDao.getCUsers(usernames);
	}

	@Override
	public CUser getCUser(long id) {
		return cuserDao.getCUser(id);
	}

	@Override
	public CUser createCUser(String telephone, String wechatId,
			String wechatName, String wechatFavicon ,String openId) {
		
		return cuserService.createCUser(telephone, wechatId, wechatName, wechatFavicon ,openId);
	}
	

	@Override
	public CUser updateInfo(String username, Map<String, Object> Info) {
		if(Info==null || username==null ) return  null;
		return cuserService.updateInfo(username, Info);
		
	}
}
