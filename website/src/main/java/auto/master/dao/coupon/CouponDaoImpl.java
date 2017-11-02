package auto.master.dao.coupon;

import org.springframework.stereotype.Repository;

import auto.dao.impl.DaoImpl;
import auto.datamodel.dao.ProxyCoupon;

@Repository("masterCouponDao")
public class CouponDaoImpl extends DaoImpl implements ICouponDao {

	@Override
	public void saveProxyCoupon(ProxyCoupon proxyCoupon) {
		save(proxyCoupon);
	}
	
}
