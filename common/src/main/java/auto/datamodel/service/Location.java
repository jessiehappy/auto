package auto.datamodel.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import auto.util.CommonUtils;

@Data
@ToString
@AllArgsConstructor
public class Location {

    public static final Location BEIJING = new Location(116.39564503788,
            39.92998577808);

    /**
     * Longitude.
     */
    private double x;

    /**
     * Latitude.
     */
    private double y;
    
    /**
     * address
     */
    private String address;

    public boolean isValid() {
        if (x <= 0 || y <= 0 || this.equals(BEIJING)) {
            return false;
        }
        return CommonUtils.isValidLocation(x, y);
    }
    
    public Location (double x, double y) {
        this.x = x;
        this.y = y;
    }

}