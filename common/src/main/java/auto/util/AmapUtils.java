/**
 * 
 */
package auto.util;

import java.net.URLEncoder;
import java.util.List;

import lombok.ToString;
import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;

import auto.datamodel.service.Location;

@CommonsLog
public class AmapUtils {
    
    private static final String URL = "http://restapi.amap.com/v3/geocode/geo?key=418f4e0e045308984eb4c81c7aa25bd2&s=rsv3&city=010&address=%s";
    
    private static final String SUCCESS = "1";
    
    public static Location getLocation(String position) throws Exception {
        String url = String.format(URL, URLEncoder.encode(position, "utf8"));
        Result result = HttpUtils.get(url, Result.class);
        log.info("get amap location, position = " + position + ", result = " + result);
        if (result.isSuccess()) {
            if (CollectionUtils.isNotEmpty(result.geocodes)) {
                GeoCode geoCode = result.geocodes.get(0);
                if (StringUtils.isNotEmpty(geoCode.location)) {
                    String[] ss = geoCode.location.split(",");
                    if (ss.length == 2) {
                        double x = NumberUtils.toDouble(ss[0], -1);
                        double y = NumberUtils.toDouble(ss[1], -1);
                        if (x >=0 && y >= 0) {
                            Location location = new Location(x, y, position);
                            return CoordinateUtils.convertGCJ2Baidu(location);
                        }
                    }
                }
            }
        } else {
            log.error("get location from amap failed, position : " + position + ", info : " + result.info);
        }
        return null;
    }
    
    @ToString
    private static class Result {
        public String status;
        public String info;
        public List<GeoCode> geocodes;
        public boolean isSuccess() {
            return SUCCESS.equals(status);
        }
    }
    
    @ToString
    private static class GeoCode {
        public String province;
        public String city;
        public String district;
        public String cityCode;
        public String formatted_address;
        public String location;
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(getLocation("牡丹园"));
    }

}
