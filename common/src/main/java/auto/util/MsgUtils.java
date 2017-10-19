package auto.util;

import java.util.HashMap;
import java.util.Map;

import com.qiniu.api.net.Http;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import auto.Properties;

@CommonsLog
@SuppressWarnings("unused")
public class MsgUtils {

        private static final String YUNPIAN_URL = "http://yunpian.com/v1/sms/send.json";
        
        private static final String YUNPIAN_APIKEY = "62821b427e2395d71fc0cadc85fc1052";
        
        private static final String ZTSMS_URL = "http://www.ztsms.cn:8800/sendXSms.do";
        
        private static final String ZTSMS_USERNAME = "yilingqijian";
        
        private static final String ZTSMS_PASSWORD = "Room107room";
        
        private static final String ZTSMS_NOTICE_PRODUCT = "887362";
        
        private static final String ZTSMS_AD_PRODUCT = "435227";
        
        private static final String MENGWANG_API_KEY = "5dd8b9e9872386b35b13d166cf8fe918"; 
        //private static final String MENGWANG_API_KEY =   "b7632ab642fbaf8a887f1b49fb21eb88"; 
        
        private static final String MENGWANG_BEIFANG_URL = "http://api02.monyun.cn:7901"; 
        
        private static final String MENGWANG_NANFANG_URL = "http://api01.monyun.cn:7901"; 
        
        private static final String SINGOLE_SMS_PATH  = "/sms/v2/std/single_send";
        
        
//        private static final String CHUANGMING_URL = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
//        
//        private static final String CHUANGMING_ACCOUNT = "1001@501133430001";
//        
//        private static final String CHUANGMING_KEY = "2ED72E37340A9D6FD2B6122FF729907B";
//        
//        private static final String CHUANGMING_CHANNEL = "5329";

        public static boolean sendTemplateSms(String text, String mobile) {
        		Map<String, String> params = new HashMap<String, String>();
            params.put("apikey", MENGWANG_API_KEY);
            params.put("content", text);
            params.put("mobile", mobile);
            try {
            		//梦网短信发送要求gbk编码
                String response = HttpUtils.post(MENGWANG_BEIFANG_URL+SINGOLE_SMS_PATH, params, HttpUtils.GBK);
                MengWangResult result = JsonUtils.fromJson(response, MengWangResult.class);
                log.info("send a template message to mobile : " + mobile + ", text : " + text + ", response : " + response);
                if (result.result == 0) {
                    return true;
                } else {
                    return false;
                }
            } catch(Exception e) {
                log.error(e, e);
                return false;
            }
        }
        private static boolean sendSms(String text, String mobile, String productId) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", ZTSMS_USERNAME);
            params.put("password", ZTSMS_PASSWORD);
            params.put("mobile", mobile);
            params.put("content", text + " 退订回T");
            params.put("productid", productId);
            params.put("xh", "");
            if (Properties.isMsgOn) {
                try {
                    String response = HttpUtils.post(ZTSMS_URL, params);
                   
                    log.info("send a message to mobile : " + mobile + ", text : " + text + ", response : " + response);
                    if (response.contains("OK")) {
                        return true;
                    } else {
                        return false;
                    }
                } catch(Exception e) {
                    log.error(e, e);
                    return false;
                }
            } else {
                log.info("send a message to mobile : " + mobile + ", text : " + text);
                return true;
            }
        }
        
        public static boolean sendNoticeSms(String text, String mobile) {
            return sendSms(text, mobile, ZTSMS_NOTICE_PRODUCT);
        }
        
        public static boolean sendAdSms(String text, String mobile) {
            return sendSms(text, mobile, ZTSMS_AD_PRODUCT);
        }
        
        /*Map<String, String> params = new HashMap<String, String>();
        params.put("action", "sendOnce");
        params.put("ac", CHUANGMING_ACCOUNT);
        params.put("authkey", CHUANGMING_KEY);
        params.put("m", mobile);
        params.put("c", text + " 回T退订");
        params.put("cgid", CHUANGMING_CHANNEL);
        if (Properties.isMsgOn) {
            String response = WebUtils.post(CHUANGMING_URL, params);
            log.info("send a message to mobile : " + mobile + ", text : " + text + ", response : " + response);
            if (response.contains("result=\"1\"")) {
                return true;
            } else {
                return false;
            }
        } else {
            log.info("send a message to mobile : " + mobile + ", text : " + text);
            return true;
        }*/
        
        private static class SendMsg implements Runnable {
            
            private String content;
            
            private String telephone;
            
            public SendMsg(String content, String telephone) {
                this.content = content;
                this.telephone = telephone;
            }

            @Override
            public void run() {
                sendAdSms(content, telephone);
            }
            
        }
        
        public static void main(String[] args) throws Exception {
            System.out.println(sendTemplateSms("您的验证码是12321，在5分钟内输入有效。如非本人操作请忽略此短信。","15712929973"));
        }
}
@Data
class MengWangResult{
	Integer result;
	String msgid;
	String custid;
}
