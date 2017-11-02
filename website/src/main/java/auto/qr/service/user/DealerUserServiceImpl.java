package auto.qr.service.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.BasicJson;
import auto.datamodel.dao.DealerAuth;
import auto.datamodel.dao.DealerUser;
import auto.datamodel.dao.ProxyAuth;
import auto.datamodel.service.DealerUserResult;
import auto.qr.dao.user.IDealerUserDao;
import auto.util.StringUtils;

@CommonsLog
@Service
@Transactional
public class DealerUserServiceImpl implements IDealerUserService {

    @Autowired
    private IDealerUserDao duserDao;
    
    @Qualifier(value = "masterDUserService")
    @Autowired
    private auto.master.service.user.IDealerUserService duserService;
    
    @Qualifier(value = "masterDAuthService")
    @Autowired
    private auto.master.service.user.IDealerAuthService dauthService;
    
    @Autowired
    private IUserService userService;

	@Override
	public DealerUser getDUser(String username) {
		return StringUtils.isEmpty(username)?null:duserDao.getDUser(username);
	}

	@Override
	public List<DealerUser> getDUsers(List<String> usernames) {
		return duserDao.getDUsers(usernames);
	}

	@Override
	public DealerUser getDUser(long id) {
		return duserDao.getDUser(id);
	}

	@Override
	public DealerUser createDUser(String telephone, String password) {
		
		return duserService.createDUser(telephone, password);
	}
	

	@Override
	public DealerUser updateInfo(String username, Map<String, Object> Info) {
		if(Info==null || username==null ) return  null;
		return duserService.updateInfo(username, Info);
		
	}

	@Override
	public BasicJson register(String deviceId, String telephone,
			String phoneNum, String picCode, String password) {
		BasicJson result=null;
		boolean flagPic=userService.verifyPicCode(deviceId, picCode);
		boolean flagPhone=userService.verifyPhoneNum(deviceId, phoneNum);
		//图形验证
		if(!flagPic){
			result=new BasicJson(JsonStatus.PICCODE_CODE_ERROR, JsonStatus.piccode_msg_error);
		}else{
			//短信验证
			if(!flagPhone){
	           result=new BasicJson(JsonStatus.PHONECODE_CODE_ERROR, JsonStatus.phonecode_msg_error);
			}else{
				DealerUser user=createDUser(telephone,password);
				DealerUserResult userResult=new DealerUserResult(user);
				result=new BasicJson(userResult);
			}
		}
		
		return result;
	}

	@Override
	public Boolean reset(String username,String oldPS, String newPS) {
		boolean flag=false;
		DealerUser user=duserDao.getDUser(username);
		if(oldPS.equals(user.getPassword())){
			Map<String, Object>info=new HashMap<String, Object>();
			info.put("password", newPS);
			duserService.updateInfo(username, info);
			flag=true;
		}
		return flag;	
	}

	@Override
	public boolean editPhone(String username, String telephone,
			String deviceId, String phoneNum) {
		boolean flag=false;
		boolean flagPhone=userService.verifyPhoneNum(deviceId, phoneNum);
		if(flagPhone){
			Map<String, Object>info=new HashMap<String, Object>();
			info.put("telephone", telephone);
			duserService.updateInfo(username, info);
			flag=true;
		}
		return flag;
	}

	@Override
	public boolean editGender(String username, String gender) {
		Map<String, Object>info=new HashMap<String, Object>();
		info.put("gender", Integer.valueOf(gender));
		duserService.updateInfo(username, info);
		return true;
	}

	@Override
	public boolean editNickname(String username, String nickName) {
		Map<String, Object>info=new HashMap<String, Object>();
		info.put("nickName", nickName);
		duserService.updateInfo(username, info);
		return true;
	}

	@Override
	public boolean editFavicon(String username, String favicon) {
		Map<String, Object>info=new HashMap<String, Object>();
		info.put("favicon", favicon);
		duserService.updateInfo(username, info);
		return true;
	}

	@Override
	public DealerAuth createDAuth(String username, String telephone, String address,
			String company, String businessLicence, BigDecimal longitude,
			BigDecimal latitude) {
		return dauthService.createDAuth(username, telephone, address, company, businessLicence, longitude, latitude);
	}

	@Override
	public DealerAuth updateDAuthInfo(String username, Map<String, Object> info) {
		return dauthService.updateInfo(username, info);
	}

	@Override
	public void updateDAuthStatus(String username, Map<String, Object> info) {
		DealerAuth dAuth=dauthService.updateInfo(username, info);
		DealerUser dealer=duserService.updateInfo(username, info);
	}
}
