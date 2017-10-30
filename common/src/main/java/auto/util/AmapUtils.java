/**
 * 
 */
package auto.util;

import java.net.URLEncoder;
import lombok.ToString;
import lombok.extern.apachecommons.CommonsLog;

import auto.datamodel.service.Location;

@CommonsLog
public class AmapUtils {
    
//    private static final String URL = "http://restapi.amap.com/v3/geocode/geo?key=418f4e0e045308984eb4c81c7aa25bd2&s=rsv3&city=010&address=%s";
	//高德地图
	//    private static final String URL = "http://restapi.amap.com/v3/geocode/geo?key=b6e6cdc808a47530102b60305a22a3bd&address=%s&s=auto-key";
    //腾讯地图
	private static final String URL = "http://apis.map.qq.com/ws/geocoder/v1/?address=%s&key=FAUBZ-4EX3O-365WL-S6UES-U2Z4O-MOBXV&sn=gRwEZtmMixaRotPkUo5HuNHl5WePdz";
    private static final String SUCCESS = "0";
    
    public static Location getLocation(String position) throws Exception {
        String url = String.format(URL, URLEncoder.encode(position, "utf8"));
        Result result = HttpUtils.get(url, Result.class);
        System.out.println(JsonUtils.toJson(result));
        log.info("get amap location, position = " + position + ", result = " + result);
        if (result.isSuccess()) {
        		double x = result.result.location.lng;
        		double y = result.result.location.lat;
            Location location = new Location(x, y, position);
            return CoordinateUtils.convertGCJ2Baidu(location);
        } else {
            log.error("get location from amap failed, position : " + position + ", info : " + result.message);
        }
        return null;
    }
    
    @ToString
    private static class Result {
    		public boolean isSuccess() {
			// TODO Auto-generated method stub
			return SUCCESS.equals(status);
		}
			public String status;
    		public String message;
    		public Content result;
    }
    
    @ToString
    private static class Content {
    		public String title;
    		public String similarity;
    		public String deviation;
    		public String reliability;
    		public location location;
    		public components address_components;
    }
    
    @ToString
    private static class location {
    		public double lng;
    		public double lat;
    }
    
    @ToString
    private static class components {
    		public String province;
    		public String city;
    		public String district;
    		public String street;
    		public String street_number;
    }
    public static void main(String[] args) throws Exception {
        System.out.println(getLocation("陇西一中")); 
    }
}
