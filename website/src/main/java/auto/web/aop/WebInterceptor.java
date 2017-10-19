package auto.web.aop;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import auto.util.ServiceDegradeManager;
import auto.util.AccessLog;
import auto.util.StringUtils;
import auto.util.WebUtils;
import auto.web.session.IpSessionManager;

@CommonsLog
public class WebInterceptor extends HandlerInterceptorAdapter {

    private static final String IP = "115.28.42.203";
    
    private static final String[] BLACK_IP_LIST = {
    };
    
    private static final String[] WHITE_IP_LIST = {
    };
    
    private static final Set<String> BLACK_IPS = new HashSet<String>();
    
    private static final Set<String> WHITE_IPS = new HashSet<String>();
    
    static {
        for (String ip : BLACK_IP_LIST) {
            BLACK_IPS.add(ip);
        }
        for (String ip : WHITE_IP_LIST) {
            WHITE_IPS.add(ip);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        AccessLog.startAccess(request);
        StringBuffer buffer = request.getRequestURL();
        int i = buffer.indexOf(IP);
        if (i >= 0) {
            buffer.replace(i, i + IP.length(), "107room.com");
            response.sendRedirect(buffer.toString());
            return false;
        }
        String ip = WebUtils.getRealIp(request);
        String platform = WebUtils.getNullIfEmpty(request, "platform");
        if(platform == null) platform = "other";
        if (BLACK_IPS.contains(ip) && !"MINIAPP".equals(platform)) {
            return false;
        }
        if (!request.getRequestURI().contains("static") &&
            !request.getRequestURI().contains("admin") &&
            !"MINIAPP".equals(platform) &&
            !WHITE_IPS.contains(ip)) {
            if (IpSessionManager.isRequestLimited(ip)) {
                addBlackIp(ip);
//                EmailUtils.sendSystemMail("request frequency trigger threshold", "ip = " + ip);
                return false;
            }
        }
        Integer channel = WebUtils.getInt(request, "channel", null);
        if (channel != null) {
//            WebUtils.setCookie(response, WebUtils.CHANNEL_COOKIE, String.valueOf(channel));
        } else {
//            String str = WebUtils.getCookieValue(request, WebUtils.CHANNEL_COOKIE);
        String str = "";    
        	try {
                if (str != null) {
                    channel = Integer.valueOf(str);
                    request.setAttribute("channel", channel);
                }
            } catch(Exception e) {}
        }
        ServiceDegradeManager.startRequest(request);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        ServiceDegradeManager.finishRequest(request);
//        Platform platform = WebUtils.getPlatform(request);
//        if (platform == Platform.pc && !request.getRequestURI().contains("verifyCode")) {
//            WebUtils.setRobotCookieId(request, response);
//        }
        if (modelAndView != null) {
            // set whether in embedded model
            WebUtils.checkEmbedded(request, modelAndView.getModel());
        }
    }
    
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (!request.getRequestURI().contains("static") &&
            !request.getRequestURI().contains("error")) {
            AccessLog.log(request, response);
        }
    }
    
    public static void addBlackIp(String ip) {
        if (StringUtils.isNotEmpty(ip) && !WHITE_IPS.contains(ip)) {
            BLACK_IPS.add(ip);
            log.info("add black ip : " + ip);
        }
    }
    public static boolean getNO_ACCESS_IP(String[] targetIp,String ip){
        if("".equals(ip)) return false;
        for(int i=0;i<targetIp.length;i++){
            if(ip.matches(targetIp[i])) return true;
        }
        return false;
    }
    
}
