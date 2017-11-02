package auto.master.service.coupon;

import auto.datamodel.dao.ProxyUser;

public interface ICouponService {

	Integer generateCoupon(Long id, ProxyUser proxyUser, Integer couponQuota);

}
