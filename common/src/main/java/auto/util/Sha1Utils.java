package auto.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.apachecommons.CommonsLog;

/**
 * Thread-safe.
 * 
 */
@CommonsLog
public class Sha1Utils {
    
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static final ThreadLocal<MessageDigest> LOCAL_SHA1 = new ThreadLocal<MessageDigest>() {
        @Override
        protected MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("SHA1");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static String sha1(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            MessageDigest messageDigest = LOCAL_SHA1.get();
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            log.error(e, e);
            return null;
        }
 
    }
    
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
    
    public static void main(String[] args) throws Exception {
        String s = "jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value";
        System.out.println(sha1(s));
        
    }
    
}
