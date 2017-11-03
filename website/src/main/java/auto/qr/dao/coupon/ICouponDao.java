package auto.qr.dao.coupon;

import java.util.List;

import auto.dao.IReadonlyDao;
import auto.datamodel.dao.Coupon;

public interface ICouponDao extends IReadonlyDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	Coupon getCoupon(long id);
	/**
	 * 
	 * @param username
	 * @return
	 */
	Coupon getCoupon(String username);
	/**
	 * 
	 * @param username
	 * @return
	 */
	List<Coupon> getCoupons(String username);
	
    

}
