package auto.qr.service.wx;

import org.springframework.stereotype.Service;

import auto.util.HttpUtils;
import auto.util.JsonUtils;
@Service
public class WXServiceImpl implements WXService {
	
	private final static String appid = "wx20ca2305a3b601f1";
	
	private final static String secret = "111fbfb07fd07dc39bef0791dfe27597";
	
	@Override
	public String getOpenId(String code) throws Exception {
		 //拼接参数  
        String param = "?grant_type=authorization_code&appid=" + appid + "&secret=" + secret + "&js_code=" + code;   
		//请求
        String temp = HttpUtils.get("https://api.weixin.qq.com/sns/jscode2session" + param);
		
        WXResult result=JsonUtils.fromJson(temp, WXResult.class);
		
        return result==null ? null : result.getOpenid();
	}
}
