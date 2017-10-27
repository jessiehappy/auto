package auto.qr.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auto.datamodel.dao.PUser;
import auto.datamodel.dao.PAuth;
import auto.datamodel.service.PUserResult;
import auto.qr.dao.user.IPUserDao;
import auto.util.ImageUploadUtils;
import auto.util.ImageUtils;
import auto.util.StringUtils;

@CommonsLog
@Service
@Transactional
public class PUserServiceImpl implements IPUserService {

    @Autowired
    private IPUserDao puserDao;
    
    @Qualifier(value = "masterPUserService")
    @Autowired
    private auto.master.service.user.IPUserService puserService;
    
    @Qualifier(value = "masterPAuthService")
    @Autowired
    private auto.master.service.user.IPAuthService pauthService;

	@Override
	public PUser getPUser(String username) {
		return StringUtils.isEmpty(username)?null:puserDao.getPUser(username);
	}

	@Override
	public List<PUser> getPUsers(List<String> usernames) {
		return puserDao.getPUsers(usernames);
	}

	@Override
	public PUser getPUser(long id) {
		return puserDao.getPUser(id);
	}

	@Override
	public PUser createPUser(String telephone, String wechatId,
			String wechatName, String wechatFavicon ,String openId) {
		
		return puserService.createPUser(telephone, wechatId, wechatName, wechatFavicon ,openId);
	}
	

	@Override
	public PUser updateInfo(String username, Map<String, Object> Info) {
		if(Info==null || username==null ) return  null;
		return puserService.updateInfo(username, Info);
		
	}

	@Override
	public int identifyPUser(String telephone) {
        int result=0;
		PUser user=getPUser(telephone);
		if(user!=null){
			result=user.getStatus();
		}
		return result;
	}

	@Override
	public PUser switchPUser(String telephone, String openId) {
		//根据openId查询是否存在用户
		PUser oldUser=getPUser(openId);
		if(oldUser!=null){
			//编辑oldUser,删除openId
			Map<String,Object> Info=new HashMap<String, Object>();
			Info.put("openId", "");
			updateInfo(oldUser.getUsername(), Info);
			//保存newUser
			PUser newUser=createPUser(telephone, null, null, null, openId);
			//更新newUser的认证状态
			Map<String,Object> Info2=new HashMap<String, Object>();
			Info.put("status", oldUser.getStatus());
			updateInfo(newUser.getUsername(), Info2);

			return newUser;
		}
		return null;
	}

	@Override
	public PAuth createPAuth(String telephone, String realName, String idNo,
			String frontImg, String qualification) {
		return pauthService.createPAuth(telephone,realName, idNo,frontImg,qualification);
	}

	@Override
	public PAuth updatePAuthInfo(String username, Map<String, Object> info) {
		return pauthService.updateInfo(username, info);
	}

	
}
