package auto.qr.service.user;

import java.util.Random;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auto.util.VerifyCodeUtils;
import auto.util.MsgUtils;
import auto.util.RedisManager;
import auto.util.ValidateUtils;

@CommonsLog
@Service
@Transactional
public class UserServiceImpl implements IUserService {
    
    private static final long USER_EXPIRE_TIME = 1000l * 60 * 60 * 24 * 7;
    
    private static final int PHONENUM_EXPIRE_TIME = 60 * 2 ;//短信验证码  过期时间  2分钟
    
    private static final int PICCODE_EXPIER_TIEM = 60*2 ;//图片验证码 过期时间 30秒

	@Override
	public boolean sendPhoneNum(String telephone,String openId) {

		boolean result=false;
		if (ValidateUtils.validatePhone(telephone)) {
			//发送短信内容中的验证码
			String phoneNum=new Random().nextInt(999999)+"";
			//发送的短信内容
			String content="您的注册验证码是"+phoneNum+"，在"+(PHONENUM_EXPIRE_TIME/60)+"分钟内输入有效。如非本人操作请忽略此短信。";
			
			boolean flag=MsgUtils.sendTemplateSms(content, telephone);
			if(flag){
				byte[] key=(openId+"_phoneNum").getBytes();
				RedisManager.set(key, phoneNum.getBytes(), PHONENUM_EXPIRE_TIME);
				result=true;
			}
		}
		return result;
	}

	@Override
	public String sendGraphCode(String openId) {
		try {
			int w = 186, h = 72;
			String verifyCode = VerifyCodeUtils.calculateVerifyCode();
			
			String graphCodeUrl=VerifyCodeUtils.outputImage(w, h, null, verifyCode);
			
			byte[] key=(openId+"_picCode").getBytes();
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
	public boolean verifyPhoneNum(String openId, String phoneNum) {
		byte[] key=(openId+"_phoneNum").getBytes();
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
	public boolean verifyPicCode(String openId, String picCode) {
		byte[] key=(openId+"_picCode").getBytes();
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
}
