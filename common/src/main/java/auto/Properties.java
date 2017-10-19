package auto;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class Properties {
    
	private static final String[] IMAGE_SERVER = new String[] {
			"127.0.0.1"
	};
    private static final String[] ONLINE_SERVER = new String[]{
        "182.92.162.81", "123.57.14.13", "182.92.165.178", "182.92.232.45", "182.92.231.125",
        "123.56.84.155", "123.56.155.89",
        "10.171.114.27", "10.172.217.21", "10.172.195.104", "10.171.98.178", "10.171.100.219",
        "10.170.209.238", "10.170.240.107"};
    
    private static final String[] JOB_SERVER = new String[]{
        "182.92.162.81", "10.171.114.27",};
    
    private static final String[] ONLINE_TEST_SERVER = new String[] {
        "101.200.204.65",  "10.44.176.141"};
    
    public static String HOST = "http://107room.com";
    
    public static boolean isPushOn = false;
    
    public static boolean isJobOn = false;
    
    public static boolean isEmailOn = false;
    
    public static boolean isMsgOn = false;
    
    public static boolean isNotifyOn = false;
    
    public static boolean isOnline = false;
    
    public static final String ip;
    
    public static final String hostName;
    
    public static final Map<String, String> HOSTNAME2NAME = new HashMap<String, String>();
    
    public static final String IMAGE_HOST = "http://localhost:8080";
    
    static {
        HOSTNAME2NAME.put("AY1407072222425403feZ", "online-0");
        HOSTNAME2NAME.put("iZ25yd71kssZ", "online-1");
        HOSTNAME2NAME.put("iZ25exjyn0nZ", "online-test");
        HOSTNAME2NAME.put("iZ25kuh629jZ", "crawler001");
        HOSTNAME2NAME.put("iZ25tvf109zZ", "crawler002");
        HOSTNAME2NAME.put("iZ25tpvb977Z", "crawler003");
        HOSTNAME2NAME.put("iZ25arxi6zgZ", "crawler004");
        HOSTNAME2NAME.put("iZ2522yc1f2Z", "crawler005");
        
        String initIp = "";
        String initHostName = "unkonwn host";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            initIp = addr.getHostAddress().toString();
            initHostName = addr.getHostName();
        } catch (Exception e) {
            log.error(e, e);
        }
        ip = initIp;
        hostName = HOSTNAME2NAME.containsKey(initHostName) ? HOSTNAME2NAME.get(initHostName) : initHostName;
        HOST = "http://" + ip;
        for (String server : ONLINE_SERVER) {
            if (server.equals(ip)) {
                isPushOn = true;
                isEmailOn = true;
                isMsgOn = true;
                isNotifyOn = true;
                isOnline = true;
                HOST = "http://107room.com";
            }
        }
        for (String server : JOB_SERVER) {
            if (server.equals(ip)) {
                isJobOn = true;
            }
        }
        for (String server : ONLINE_TEST_SERVER) {
            if (server.equals(ip)) {
                HOST = "http://101.200.204.65:8080";
                isPushOn = true;
//                isEmailOn = true;
//                isMsgOn = true;
                isNotifyOn = true;
                isJobOn = true;
            }
        }
        for(String server: IMAGE_SERVER) {
        		if(server.equals(ip)) {
        			HOST = "http://localhost:8080/";
        		}
        }
        log.info("init properties finished.");
        log.info("ip = " + ip);
        log.info("host = " + HOST);
        log.info("hostName = " + hostName);
        log.info("isOnline = " + isOnline);
        log.info("isPushOn = " + isPushOn);
        log.info("isJobOn = " + isJobOn);
        log.info("isEmailOn = " + isEmailOn);
        log.info("isMsgOn = " + isMsgOn);
        log.info("isNotifyOn = " + isNotifyOn);
    }
    public static void main(String[] args) {
		System.out.println(Properties.HOST);
	}
}
