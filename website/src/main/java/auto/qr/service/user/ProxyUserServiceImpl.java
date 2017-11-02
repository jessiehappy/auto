package auto.qr.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auto.datamodel.dao.ProxyUser;
import auto.datamodel.dao.ProxyAuth;
import auto.qr.dao.user.IProxyAuthDao;
import auto.qr.dao.user.IProxyUserDao;
import auto.util.StringUtils;

@CommonsLog
@Service
@Transactional
public class ProxyUserServiceImpl implements IProxyUserService {

    @Autowired
    private IProxyUserDao puserDao;
    
    @Qualifier(value = "masterPUserService")
    @Autowired
    private auto.master.service.user.IProxyUserService puserService;
    
    @Qualifier(value = "masterPAuthService")
    @Autowired
    private auto.master.service.user.IProxyAuthService pauthService;
    
    @Autowired
    private IProxyAuthDao pauthDao;

	@Override
	public ProxyUser getPUser(String username) {
		return StringUtils.isEmpty(username)?null:puserDao.getPUser(username);
	}

	@Override
	public List<ProxyUser> getPUsers(List<String> usernames) {
		return puserDao.getPUsers(usernames);
	}

	@Override
	public ProxyUser getPUser(long id) {
		return puserDao.getPUser(id);
	}

	@Override
	public ProxyUser createPUser(String telephone, String wechatId,
			String wechatName, String wechatFavicon ,String openId) {
		//查询用户是否存在
		ProxyUser user=puserDao.getPUser(telephone);
		//用户不存在，创建用户
		if(user==null){
			user=puserService.createPUser(telephone, wechatId, wechatName, wechatFavicon ,openId);
		}
		
		return user;
	}
	

	@Override
	public ProxyUser updateInfo(String username, Map<String, Object> Info) {
		if(Info==null || username==null ) return  null;
		return puserService.updateInfo(username, Info);
		
	}

	@Override
	public int identifyPUser(String telephone) {
        int result=0;
		ProxyUser user=getPUser(telephone);
		if(user!=null){
			result=user.getStatus();
		}
		return result;
	}

	@Override
	public ProxyUser switchPUser(String telephone, String openId) {
		//根据openId查询是否存在用户
		ProxyUser oldUser=getPUser(openId);
		//根据openId 查询用户的认证状态
		int status=getPAuth(openId).getStatus();
		if(oldUser!=null){
			//编辑oldUser,删除openId
			Map<String,Object> Info=new HashMap<String, Object>();
			Info.put("openId", "");
			updateInfo(oldUser.getUsername(), Info);
			
			//根据telephone查询切换的用户是否存在，如果存在就进行编辑，不存在就创建新的用户
			ProxyUser newUser=null;
			newUser=getPUser(telephone);
			Map<String,Object> Info2=new HashMap<String, Object>();
			if(newUser!=null){
				//更新切换用户的认证状态
				Info2.put("openId", openId);
				Info2.put("status",status);
				updateInfo(newUser.getUsername(), Info2);
			}else{
				//保存newUser
				newUser=createPUser(telephone, null, null, null, openId);
				//更新newUser的认证状态
				Info2.put("status", status);
				updateInfo(newUser.getUsername(), Info2);
			}
			return newUser;	
		}
		return null;
	}

	@Override
	public ProxyAuth createPAuth(String telephone, String realName, String idNo,
			String frontImg, String qualification, String longitude, String latitude) {
		ProxyUser user=puserDao.getPUser(telephone);
		String username=user.getUsername();
		String openId=user.getOpenId();
		return pauthService.createPAuth(openId,username,telephone,realName, idNo,frontImg,qualification,longitude,latitude);
	}

	@Override
	public ProxyAuth updatePAuthInfo(String username, Map<String, Object> info) {
		return pauthService.updateInfo(username, info);
	}

	@Override
	public ProxyAuth getPAuth(String username) {
		return pauthDao.getPAuth(username);
	}

	@Override
	public void updatePAuthStatus(String username, Map<String, Object> info) {
		ProxyUser pUser=puserDao.getPUser(username);
		//proxy_user表更改认证状态
		this.updateInfo(username, info);
		//proxy_auth表更改认证状态
		pauthService.updateInfo(pUser.getUsername(), info);
		
	}

	
}
