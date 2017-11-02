package auto.qr.dao.coupon;

import java.util.List;

import auto.datamodel.dao.ProxyCoupon;

public interface IProxyCouponDao {

	List<ProxyCoupon> getProxyCouponByProxyUsername(String username);

}
