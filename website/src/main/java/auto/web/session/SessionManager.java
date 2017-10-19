package auto.web.session;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.util.StringUtils;

import auto.util.RedisManager;
import auto.util.SerializeUtils;
import auto.util.WebUtils;

@CommonsLog
public class SessionManager {
    
    private static final String SESSION_COOKIE = "AUTO_SESSION_ID";
    
    private static final String SESSION_PREFIX = "session_";
    
    private static final int SESSION_EXPIRE_TIME_IN_SECONDS = 60 * 60;
    
    private static final String COOKIE_CODES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    private static final int SESSION_COOKIE_LENGTH = 25;
    
    private static String generateSessionCookie() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SESSION_COOKIE_LENGTH; i++) {
            sb.append(COOKIE_CODES.charAt(rand.nextInt(COOKIE_CODES.length())));
        }
        return sb.toString();
    }
    
    private static boolean remove(String id) {
        if (StringUtils.isEmpty(id)) return false;
        id = SESSION_PREFIX + id;
        try {
            return RedisManager.del(id.getBytes());
        } catch(Exception e) {
            log.error("remove session failed. id = " + id, e);
            return false;
        }
    }
    
    private static boolean set(String id, Session value) {
        if (StringUtils.isEmpty(id) || value == null) return false;
        id = SESSION_PREFIX + id;
        try {
            byte[] v = SerializeUtils.serialize(value);
            return RedisManager.set(id.getBytes(), v, SESSION_EXPIRE_TIME_IN_SECONDS);
        } catch(Exception e) {
            log.error("set session failed. id = " + id, e);
            return false;
        }
    }
    
    private static Session get(String id, int expireTime) {
        if (StringUtils.isEmpty(id)) return null;
        id = SESSION_PREFIX + id;
        try {
            byte[] bytes = RedisManager.get(id.getBytes(), expireTime);
            Session session = SerializeUtils.deseialize(bytes, Session.class);
            return session == null ? new Session() : session;
        } catch(Exception e) {
            log.error("get session failed. key = " + id);
            return new Session();
        }
    }
    
    private static Session get(String id) {
        return get(id, SESSION_EXPIRE_TIME_IN_SECONDS);
    }
    
    private static String getId(HttpServletRequest request, HttpServletResponse response) {
        String id = null;
        if (!WebUtils.isMobile(request)) {
            //from cookie(in web)
            id = WebUtils.getCookieValue(request, SESSION_COOKIE);
            //from request attributes
            if (StringUtils.isEmpty(id)) {
                id = (String)request.getAttribute(SESSION_COOKIE);
                if (StringUtils.isEmpty(id)) {
                    id = generateSessionCookie();
                    request.setAttribute(SESSION_COOKIE, id);
                    WebUtils.setCookie(response, SESSION_COOKIE, id);
                }
            }
        } else {
            //from request parameters(in app)
            id = WebUtils.getDeviceId(request);
        }
        return id;
    }
    
    public static Session getSession(String id) {
        return get(id);
    }
    
    public static Session getSession(String id, int expireTime) {
        return get(id, expireTime);
    }
    
    public static <T> T getSessionValue(String id, String key) {
        Session session = getSession(id);
        if (session == null) return null;
        return session.get(key);
    }
    
    public static <T> T getSessionValue(String id, String key, int expireTime) {
        Session session = getSession(id, expireTime);
        if (session == null) return null;
        return session.get(key);
    }
    
    public static <T> T getSessionValue(String id, String key, T defaultValue) {
        T value = getSessionValue(id, key);
        return value == null ? defaultValue : value;
    }
    
    public static <T> T getSessionValue(String id, String key, T defaultValue, int expireTime) {
        T value = getSessionValue(id, key, expireTime);
        return value == null ? defaultValue : value;
    }

    public static Session getSession(HttpServletRequest request, HttpServletResponse response) {
        String id = getId(request, response);
        return getSession(id);
    }

    public static <T> T getSessionValue(HttpServletRequest request, HttpServletResponse response, String key) {
        String id = getId(request, response);
        return getSessionValue(id, key);
    }

    public static <T> T getSessionValue(HttpServletRequest request, HttpServletResponse response, String key,
            T defaultValue) {
        String id = getId(request, response);
        return getSessionValue(id, key, defaultValue);
    }
    
    public static void setSessionValue(String id, String key, Object value) throws UnsupportedTypeException {
        Session session = get(id);
        if (session != null) {
            session.set(key, value);
            set(id, session);
        }
    }
    
    public static void setSessionValue(String id, String key, Object value, int expireTime) throws UnsupportedTypeException {
        Session session = get(id, expireTime);
        if (session != null) {
            session.set(key, value);
            set(id, session);
        }
    }

    public static void removeSession(String id) {
        remove(id);
    }
    
    public static void removeSessionValue(String id, String key) {
        Session session = get(id);
        if (session != null) {
            session.remove(key);
            set(id, session);
        }
    }

    public static void setSessionValue(HttpServletRequest request, HttpServletResponse response, String key,
            Object value) throws UnsupportedTypeException {
        String id = getId(request, response);
        setSessionValue(id, key, value);
    }

    public static void removeSession(HttpServletRequest request, HttpServletResponse response) {
        String id = getId(request, response);
        removeSession(id);
    }
    
    public static void removeSessionValue(HttpServletRequest request, HttpServletResponse response, String key) {
        String id = getId(request, response);
        removeSessionValue(id, key);
    }

}
