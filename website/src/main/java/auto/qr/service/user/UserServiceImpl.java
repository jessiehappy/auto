package auto.qr.service.user;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.Flags.Flag;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auto.datamodel.GenderType;
import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.dao.User;
import auto.datamodel.service.UserResult;
import auto.util.VerifyCodeUtils;
import auto.util.MsgUtils;
import auto.util.RedisManager;
import auto.util.ValidateUtils;
import auto.datamodel.BasicJson;
import auto.qr.dao.user.IUserDao;
import auto.util.StringUtils;

@CommonsLog
@Service
@Transactional
public class UserServiceImpl implements IUserService {
    
    private static final long USER_EXPIRE_TIME = 1000l * 60 * 60 * 24 * 7;
    
    private static final int PHONENUM_EXPIRE_TIME = 60 * 2 ;//短信验证码  过期时间  2分钟
    
    private static final int PICCODE_EXPIER_TIEM = 60*2 ;//图片验证码 过期时间 30秒

    @Autowired
    private IUserDao userDao;
    
    @Qualifier(value = "masterUserService")
    @Autowired
    private auto.master.service.user.IUserService userService;

	@Override
	public User getUser(String username) {
		return StringUtils.isEmpty(username)?null:userDao.getUser(username);
	}

	@Override
	public List<User> getUsers(List<String> usernames) {
		return userDao.getUsers(usernames);
	}

	@Override
	public User getUser(long id) {
		return userDao.getUser(id);
	}

	@Override
	public User createUser(String telephone, String wechatId,
			String wechatName, String wechatFavicon) {
		
		return userService.createUser(telephone, wechatId, wechatName, wechatFavicon);
	}
	

	@Override
	public User updateInfo(String username, Map<String, Object> Info) {
		if(Info==null || username==null ) return  null;
		return userService.updateInfo(username, Info);
		
	}

	@Override
	public boolean sendPhoneNum(String telephone,String deviceId) {

		boolean result=false;
		if (ValidateUtils.validatePhone(telephone)) {
			//发送短信内容中的验证码
			String phoneNum=new Random().nextInt(999999)+"";
			//发送的短信内容
			String content="您的注册验证码是"+phoneNum+"，在"+(PHONENUM_EXPIRE_TIME/60)+"分钟内输入有效。如非本人操作请忽略此短信。";
			
			boolean flag=MsgUtils.sendTemplateSms(content, telephone);
			if(flag){
				byte[] key=(deviceId+"_phoneNum").getBytes();
				RedisManager.set(key, phoneNum.getBytes(), PHONENUM_EXPIRE_TIME);
				result=true;
			}
		}
		return result;
	}

	@Override
	public String sendGraphCode(String deviceId) {
		try {
			int w = 186, h = 72;
			String verifyCode = VerifyCodeUtils.calculateVerifyCode();
			
			String graphCodeUrl=VerifyCodeUtils.outputImage(w, h, null, verifyCode);
			
			byte[] key=(deviceId+"_picCode").getBytes();
			//计算表达式结果
			int calculateResult=VerifyCodeUtils.calculateResult(verifyCode);
			//存入redis
	        RedisManager.set(key,String.valueOf(calculateResult).getBytes(),PICCODE_EXPIER_TIEM);   
	        return graphCodeUrl;
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public boolean verifyPhoneNum(String deviceId, String phoneNum) {
		byte[] key=(deviceId+"_phoneNum").getBytes();
		byte[] temp=RedisManager.get(key);
		String telNum=null;
		boolean flag=false;
		if(temp!=null){
			telNum=new String(temp);
			if(telNum.equals(phoneNum)){
				flag=true;
			}
		}
		return flag;
	}

	@Override
	public boolean verifyPicCode(String deviceId, String picCode) {
		byte[] key=(deviceId+"_picCode").getBytes();
		byte[] temp=RedisManager.get(key);
		String imgCode=null;
		boolean flag=false;
		if(temp!=null){
			imgCode=new String(temp);
			if(imgCode.equals(picCode)){
				flag=true;
			}
		}
		return flag;
	}

	@Override
	public BasicJson login(String deviceId, String telephone, String phoneNum,
			String picCode,String wechatId,String wechatName,String wechatFavicon) {
		BasicJson result=null;
		boolean flagNum=verifyPhoneNum(deviceId, phoneNum);
		boolean flagPic=verifyPicCode(deviceId, picCode);
		
		//图形验证
		if (!flagPic) {
			result = new BasicJson(JsonStatus.PICCODE_CODE_ERROR,JsonStatus.piccode_msg_error);
		} else {
			// 短信验证
			if (!flagNum) {
				result = new BasicJson(JsonStatus.PHONECODE_CODE_ERROR,JsonStatus.phonecode_msg_error);
			} else {
				// 根据手机号码判断是否已存在
				User user = getUser(telephone);
				if (user == null) {
					user =createUser(telephone, wechatId, wechatName, wechatFavicon);
				}
				UserResult userResult = new UserResult(user);
				result = new BasicJson(userResult);
			}
		}

		return result;
	}
}
