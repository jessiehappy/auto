package auto.util;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.apachecommons.CommonsLog;
import auto.datamodel.Platform;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.mobile.device.site.SitePreferenceUtils;

@CommonsLog
public class WebUtils {

    private static final int COOKIE_DEFAULT_TTL = 3600 * 24 * 3650; // 10 years

    public static final String ROBOT_JUDGE_CID = "RCID";
    
    public static final String COOKIE_LOGIN_USERNAME = "LOGIN_USERNAME";
    
    /*
     * baidu
     */
    public static final String BAIDU_AK_SERVER = "OLsUBbuVBbAGTL3wRr4UQEGj";

    public static final String BAIDU_AK_BROWSER = "FsL0TGj6V2tmG6juhiU6ugdz";

    public static String getBaiduAK(HttpServletRequest request) {
        return request.getRequestURL().toString().contains("://localhost:") ? BAIDU_AK_SERVER
                : BAIDU_AK_BROWSER;
    }
    
    public static String getMultiValue(HttpServletRequest request,
            String param, String defaultValue) {
        String[] values = request.getParameterValues(param);
        if (values != null) {
            return StringUtils.join(values, "|");
        }
        return defaultValue;
    }
    
    public static String getBody(HttpServletRequest request, String charset) throws Exception {
        int size = request.getContentLength();
        InputStream is = request.getInputStream();
        byte[] content = new byte[size];
        IOUtils.readFully(is, content);
        return new String(content, charset); 
    }

    public static Boolean getBoolean(HttpServletRequest request, String param,
            Boolean defaultValue) {
        try {
            String v = getNullIfEmpty(request, param);
            return !(v.equals("0") || v.equals("false"));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer getInt(HttpServletRequest request, String param,
            Integer defaultValue) {
        try {
            return Integer.parseInt(request.getParameter(param));
        } catch (Exception e) {
            try {
                if (request.getAttribute(param) != null) {
                    return (Integer)request.getAttribute(param);
                } else {
                    return defaultValue;
                }
            } catch(Exception e1) {
                return defaultValue;
            }
        }
    }
    
    public static Long getLong(HttpServletRequest request, String param,
            Long defaultValue) {
        try {
            return Long.parseLong(request.getParameter(param));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static Double getDouble(HttpServletRequest request, String param,
            Double defaultValue) {
        try {
            return Double.parseDouble(request.getParameter(param));
        } catch(Exception e) {
            return defaultValue;
        }
    }
    
    public static Float getFloat(HttpServletRequest request, String param,
            Float defaultValue) {
        try {
            return Float.parseFloat(request.getParameter(param));
        } catch(Exception e) {
            return defaultValue;
        }
    }
    
    public static boolean isMobile(HttpServletRequest request) {
        Platform platform = getPlatform(request);
        if (platform == Platform.android || platform == Platform.ios) {
            return true;
        }
        return false;
    }
    
    public static Date getDay(HttpServletRequest request, String param,
            Date defaultValue) {
        try {
            String str = WebUtils.getNullIfEmpty(request, param);
            return str == null ? defaultValue : CommonUtils.getDateFromDay(str);
        } catch(Exception e) {
            return defaultValue;
        }
    }
    
    private static String[] whiteParamList = new String[]{"signature", "nonce", "password", "token", "euid", "echostr", "iid"};
    
    private static String filterXss(String content, String param) {
        if (content == null) return null;
        for (String white : whiteParamList) {
            if (white.equals(param)) {
                return content;
            }
        }
        return content.replaceAll("<", "").replaceAll(">", "");
    }
    
    public static void main(String[] args) {
        String s = "\\u003cimg src=1 onerror=alert(/xss/)\\u003e";
        System.out.println(s.replaceAll("<", ""));
    }

    public static String getNullIfEmpty(HttpServletRequest request, String param) {
        String result = StringUtils.trimToNull(request.getParameter(param));
        if (result == null) {
            result = StringUtils.trimToNull((String)request.getAttribute(param));
        }
        return filterXss(result, param);
    }
    
    public static String getNullIfEmptyWithDecode(HttpServletRequest request, String param) {
        String str = StringUtils.trimToNull(request.getParameter(param));
        if (str != null) {
            try {
                str = URLDecoder.decode(str, "utf-8");
                return filterXss(str, param);
            } catch(Exception e) {}
        }
        return str;
    }

    public static <T> T getJson(HttpServletRequest request, String param,
            Class<T> valueClass, T defaultValue) {
        String v = request.getParameter(param);
        if (v == null) {
            return defaultValue;
        }
        try {
            return StringUtils.GSON.fromJson(v, valueClass);
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static String getWithHeaders(String url, Map<String, String> headersMap, Map<String, String> params){    	
    	HttpClient client = new DefaultHttpClient();
    	HttpGet request;
    	List<NameValuePair> paramArray = new ArrayList<NameValuePair>();
    	URI uri = null;
    	String responseText = null;
    	
    	if(params != null){    		    		
    		for(Map.Entry<String, String> param : params.entrySet()){
    			paramArray.add( new BasicNameValuePair(param.getKey(), param.getValue()));			    			
    		}    		    		
    	}
    	
        try {
            uri = new URI( url + "?" + URLEncodedUtils.format( paramArray, "utf-8" ));
        } catch (URISyntaxException e1) {
            return null;
		}
        request = new HttpGet(uri);    	    	    
    	    	
    	if(headersMap != null){
        	for(Map.Entry<String, String> header : headersMap.entrySet()){    		
        		request.addHeader(header.getKey(), header.getValue());	
        	}    		
    	}
    	
    	try{
    		HttpResponse response = client.execute(request);
    		HttpEntity entity = response.getEntity();
    		if(entity != null){
    			responseText = EntityUtils.toString(entity);
    		} else {
    			responseText = "{\"code\":500, \"msg\":\"empty response\"}";
    		}
    	}catch(Exception e){
            log.error(e, e);
            responseText = "{\"code\":500, \"msg\":\"" + e.getMessage() + "\"}";            
    	}finally{
            try {
                request.releaseConnection();
            } catch (Exception e) {
                log.error(e, e);
            }    		
    	}
    	return responseText;    	
    }

    public static int getPlatform(String deviceId) {
        if (deviceId.startsWith(Platform.android.name())) {
            return Platform.android.ordinal();
        } else if (deviceId.startsWith(Platform.ios.name())) {
            return Platform.ios.ordinal();
        }
        return Platform.pc.ordinal();
    }
    
    public static Platform getPlatform(HttpServletRequest request) {
        String platform = getNullIfEmpty(request, "platform");
        return Platform.parse(platform);
    }
    
    /**
     * @return null when sum failed
     */
    public static Integer getSum(HttpServletRequest request, String param,
            Integer defaultValue) {
        String[] values = request.getParameterValues(param);
        if (values != null) {
            try {
                int result = 0;
                for (String v : values) {
                    result += Integer.parseInt(v);
                }
                return result;
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * Called before view returned by controllers which may be embedded in other
     * pages.
     * 
     * @param request
     *            assert non-null
     * @param map
     *            model, assert non-null
     */
    public static void checkEmbedded(HttpServletRequest request,
            Map<String, Object> map) {
        String key = "embedded";
        if (getBoolean(request, key, false)) {
            map.put(key, true);
        }
    }

    public static Cookie setCookie(HttpServletResponse response, String key,
            String value) {
        Cookie result = new Cookie(key, value);
        result.setPath("/"); // global
        result.setMaxAge(COOKIE_DEFAULT_TTL);
        if (log.isDebugEnabled()) {
            log.debug("Set cookie: key=" + key + ", value=" + value);
        }
        response.addHeader("P3P", "CP=CAO PSA OUR"); // fix for ie6/ie7
        response.addCookie(result);
        return result;
    }

    public static Cookie getCookie(HttpServletRequest request, String key) {
        if (key == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (key.equals(c.getName())) {
                    return c;
                }
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie cookie = getCookie(request, key);
        return cookie == null ? null : cookie.getValue();
    }

    /**
     * 通过代理获取真实IP
     * @param request
     * @return
     */
    public static String getRealIpContainsProxy(HttpServletRequest request){
    	String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
    }
    public static String getRealIp(HttpServletRequest request) {
        String result = request.getRemoteAddr();
        //if ("127.0.0.1".equals(result)) {
        String s = request.getHeader("X-Real-IP");
        if (s != null) {
            result = s;
        }
        //}
        return result;
    }
    
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
    
    public static int getContentLength(HttpServletRequest request) {
        int len = request.getContentLength();
        if (len < 0) len = 0;
        return len;
    }
    
    public static String getUsernameInApp(HttpServletRequest request) {
        String username = getNullIfEmpty(request, "username");
        if (StringUtils.isEmpty(username)) {
            username = getDeviceId(request);
        }
        return username;
    }
    
    public static String getDeviceId(HttpServletRequest request) {
        String deviceId = getNullIfEmpty(request, "deviceId");
        String platform = getNullIfEmpty(request, "platform");
        if (StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(platform)) {
            return null;
        }
        return platform + "_" + deviceId;
    }
    
    public static Integer getChannel(HttpServletRequest request) {
        Integer channel = getInt(request, "channel", null);
        if (channel == null) {
            try {
                channel = (Integer)request.getAttribute("channel");
            } catch(Exception e) {}
        }
        return channel;
    }
    
    public static boolean isMobileByAgent(HttpServletRequest request){
        SitePreference preference = SitePreferenceUtils.getCurrentSitePreference(request);
        
        if(preference.isMobile() || preference.isTablet()){
            return true;
        }
        return false;
    }

    public static String getDomainFromEmail(String email) {
        email = StringUtils.trimToNull(email);
        if (email == null) {
            return null;
        }
        email = email.toLowerCase();
        int i = email.indexOf('@');
        return i >= 0 && i < email.length() - 1 ? email.substring(i + 1)
                : email;
    }
    
    public static Cookie setRobotCookieId(HttpServletRequest request,
            HttpServletResponse response){
        Validate.notNull(request);
        Validate.notNull(response);
        String ip = WebUtils.getRealIp(request);
        String cookieSeed = "107" + ip + "room";
        long cookieId = Math.abs(Md5Utils.sign64bit(cookieSeed.getBytes()));
        String cookieStr = String.valueOf(cookieId);
        return WebUtils.setCookie(response, ROBOT_JUDGE_CID, cookieStr);
    }

}
