package auto.qr.service.user;

import java.util.List;
import java.util.Map;

import auto.datamodel.BasicJson;
import auto.datamodel.controller.coupon.CustomCouponList;
import auto.datamodel.dao.CustomUser;


@SuppressWarnings("unused")
public interface ICustomCouponService {
	/**
	 * 根据username查询我的代金券
	 * @param username
	 * @return
	 */
	List<CustomCouponList> getMycoupons(String username);
    
}
