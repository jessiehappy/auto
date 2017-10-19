package auto.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import auto.datamodel.LogType;
import auto.datamodel.Platform;
import auto.datamodel.service.Location;

public class UserStatLog {
    
    private static final Log error = LogFactory.getLog(UserStatLog.class);

    private static final Log logger = LogFactory.getLog("event");

    public static void log(LogType type, Date timestamp, String username,
            String openId, String ip, Platform platform, Long houseId,
            Long roomId, Double longitude, Double latitude, String version,
            Integer channel, String system, String device, String deviceId,
            String moreinfo) {
        try {
            String csv = CSVUtils.toCSV(type, timestamp, username, openId, ip, platform,
                    houseId, roomId, longitude, latitude, version, channel, system, device,
                    deviceId, moreinfo);
            logger.info(csv);
        } catch(Exception e) {
            error.error(e, e);
        }
    }
    
    public static void log(LogType type, String username, String openId, Platform platform, String moreinfo) {
        log(type, new Date(), username, null, null, platform, null, null, null, null,
                null, null, null, null, null, moreinfo);
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response, String username,
            LogType type, Long houseId, Long roomId, Double latitude, Double longitude, String moreinfo) {
        try {
            String version = WebUtils.getNullIfEmpty(request, "version");
            Integer channel = WebUtils.getChannel(request);
            String system = WebUtils.getNullIfEmpty(request, "system");
            String device = WebUtils.getNullIfEmpty(request, "device");
            String deviceId = WebUtils.getNullIfEmpty(request, "deviceId");
            String ip = WebUtils.getRealIp(request);
            Platform platform = WebUtils.getPlatform(request);
            //TODO log();
        } catch(Exception e) {
            error.error(e, e);
        }
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response, String username,
            LogType type, String moreinfo) {
        log(request, response, username, type, null, null, null, null, moreinfo);
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response, String username,
            LogType type, Long houseId, Long roomId) {
        Double latitude = WebUtils.getDouble(request, "lat", null);
        Double longitude = WebUtils.getDouble(request, "lng", null);
        log(request, response, username, type, houseId, roomId, latitude, longitude, null);
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response, String username,
            LogType type, Long houseId, Long roomId, String moreinfo) {
        log(request, response, username, type, houseId, roomId, null, null, moreinfo);
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response, String username,
            LogType type, Location location, String moreinfo) {
        try {
            Double lat = null, lng = null;
            if (location != null) {
                lng = location.getX();
                lat = location.getY();
            }
            log(request, response, username, type, null, null, lng, lat, moreinfo);
        } catch(Exception e) {
            error.error(e, e);
        }
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response,
            LogType type, Long houseId, Long roomId, Double latitude, Double longitude, String moreinfo) {
        try {
            String version = WebUtils.getNullIfEmpty(request, "version");
            Integer channel = WebUtils.getChannel(request);
            String system = WebUtils.getNullIfEmpty(request, "system");
            String device = WebUtils.getNullIfEmpty(request, "device");
            String deviceId = WebUtils.getNullIfEmpty(request, "deviceId");
            String ip = WebUtils.getRealIp(request);
            Platform platform = WebUtils.getPlatform(request);
            //TODO log()
        } catch(Exception e) {
            error.error(e, e);
        }
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response, LogType type) {
        log(request, response, type, null, null, null, null, null);
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response,
            LogType type, String moreinfo) {
        log(request, response, type, null, null, null, null, moreinfo);
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response,
            LogType type, Location location, String moreinfo) {
        try {
            Double lat = null, lng = null;
            if (location != null) {
                lng = location.getX();
                lat = location.getY();
            }
            log(request, response, type, null, null, lng, lat, moreinfo);
        } catch(Exception e) {
            error.error(e, e);
        }
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response,
            LogType type, Long houseId, Long roomId) {
        Double latitude = WebUtils.getDouble(request, "lat", null);
        Double longitude = WebUtils.getDouble(request, "lng", null);
        log(request, response, type, houseId, roomId, latitude, longitude, null);
    }
    
    public static void log(HttpServletRequest request, HttpServletResponse response,
            LogType type, Long houseId, Long roomId, String moreinfo) {
        log(request, response, type, houseId, roomId, null, null, moreinfo);
    }
    
}
