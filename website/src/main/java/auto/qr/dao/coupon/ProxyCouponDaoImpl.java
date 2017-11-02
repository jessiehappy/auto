package auto.qr.dao.coupon;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.dao.ProxyCoupon;

@Repository
public class ProxyCouponDaoImpl extends ReadonlyDaoImpl implements IProxyCouponDao {

	/*@SuppressWarnings("unchecked")
	@Override
	public List<ProxyCoupon> getProxyCouponByDealerUsernameAndDealerId(
			String dealerUsername, Long id) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(ProxyCoupon.class);
		criteria.add(Restrictions.eq("dealerUsername", dealerUsername))
		 		.add(Restrictions.eq("dealerCouponId", id));
		List<ProxyCoupon> proxyCoupon = criteria.list();
		return proxyCoupon;
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<ProxyCoupon> getProxyCouponByProxyUsername(String username) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(ProxyCoupon.class);
		criteria.add(Restrictions.eq("proxyUsername",username));
		List<ProxyCoupon> proxyCoupons = criteria.list();
		return proxyCoupons;
	}
	
	
}
