package auto.util;

import java.util.Date;

public class UserUtils {
    
    public static final int AUTH_STATUS_NOT_LOGGED_IN = 0,
            AUTH_STATUS_LOGGED_IN = 1, AUTH_STATUS_VERIFIED = 2;

    
    public static String generateUsername() {
        return generateUsername(new Date());
    }
    
    public static String generateUsername(Date date) {
        return "auto_" + CommonUtils.getMillisecondFormat(date) + "_" + Md5Utils.getRandomString();
    }
    
}
