package auto.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import auto.qr.service.user.IUserService;
import auto.util.Md5Utils;
import auto.util.WebUtils;
import auto.web.session.SessionManager;
import auto.web.session.UnsupportedTypeException;

@Component
public class UserUtils extends auto.util.UserUtils {
    
    private static String COOKIE_CID = "CID";
    
    private static final String PRELOGIN_KEY = "prelogin";
    
    private static IUserService userService;
    
    @Autowired
    public void setUserService(IUserService userService) {
        UserUtils.userService = userService;
    }
    
    public static String getPreLoginUsername(HttpServletRequest request, HttpServletResponse response) {
        return SessionManager.getSessionValue(request, response, PRELOGIN_KEY);
    }
    
    public static void updatePreLoginUsername(HttpServletRequest request, HttpServletResponse response, String username) {
        try {
            SessionManager.setSessionValue(request, response, PRELOGIN_KEY, username);
        } catch (UnsupportedTypeException e) {
            //never happened
        }
    }

    
    /**
     * Check whether the cookie has cookie id.
     * @param request
     * @param response
     * @return boolean
     */
    public static boolean containCookieId(HttpServletRequest request,
            HttpServletResponse response){
        Validate.notNull(request);
        Validate.notNull(response);
        Cookie cookie = WebUtils.getCookie(request, COOKIE_CID);
        if(cookie==null){           
            return false;
        }
        return true;
    }

    public static String getCookieId(HttpServletRequest request,
            HttpServletResponse response) {
        Validate.notNull(request);
        Validate.notNull(response);
        Cookie cookie = WebUtils.getCookie(request, COOKIE_CID);
        // create if not exist
        if (cookie == null) {
            String cookieSeed = RandomUtils.nextInt() + request.getRemoteAddr()
                    + System.currentTimeMillis();
            long cookieId = Math.abs(Md5Utils.sign64bit(cookieSeed.getBytes()));
            cookie = WebUtils.setCookie(response, COOKIE_CID,
                    Long.toString(cookieId));
        }
        return cookie.getValue();
    }

    /**
     * @return null when unknown
     */
    public static String getBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.contains("msie")) {
                return "ie";
            } else if (userAgent.contains("firefox")) {
                return "firefox";
            } else if (userAgent.contains("chrome")) {
                return "chrome";
            }
        }
        return null;
    }

    /**
     * @return 0 when unknown
     */
    public static int getIEVersion(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            try {
                int i = userAgent.indexOf("msie");
                if (i > 0) {
                    i += 4;
                    String version = userAgent.substring(i, i + 3);
                    return (int) Float.parseFloat(version);
                }
            } catch (Exception e) {
            }
        }
        return 0;
    }
    
}
