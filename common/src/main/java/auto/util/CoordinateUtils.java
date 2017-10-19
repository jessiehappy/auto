/**
 * 
 */
package auto.util;

import auto.datamodel.service.Location;

public class CoordinateUtils {
    
    private static double xPi = Math.PI * 3000.0 / 180.0;
    
    public static Location convertGCJ2Baidu(Location gcj) {
        double z = Math.sqrt(gcj.getX() * gcj.getX() + gcj.getY() * gcj.getY()) +
                0.00002 * Math.sin(gcj.getY() * xPi);
        double theta = Math.atan2(gcj.getY(), gcj.getX()) + 0.000003 * Math.cos(gcj.getX() * xPi);
        Location baidu = new Location(z * Math.cos(theta) + 0.0065, z * Math.sin(theta) + 0.006);
        baidu.setAddress(gcj.getAddress());
        return baidu;
    }
    
    public static Location convertBaiduToGCJ(Location baidu) {
        double x = baidu.getX() - 0.0065;
        double y = baidu.getY() - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * xPi);  
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * xPi);
        Location gcj = new Location(z * Math.cos(theta), z * Math.sin(theta));
        gcj.setAddress(baidu.getAddress());
        return gcj;
    }

}
