package auto.qr.dao.coupon;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.dao.AutoCoupon;

@Repository
public class AutoCouponDaoImpl extends ReadonlyDaoImpl implements IAutoCouponDao {
	
	@Override
	public AutoCoupon getAutoCouponById(Long id) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(AutoCoupon.class);
		criteria.add(Restrictions.eq("dealerCouponId", id));
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AutoCoupon getAutoCoupon(Long couponId) {
		AutoCoupon autocoupon = null;
		List<AutoCoupon> autocoupons=(List<AutoCoupon>)getSession().createCriteria(AutoCoupon.class)
				.add(Restrictions.eq("couponId", couponId))
				.list();
		if(autocoupons.size()>0){
			autocoupon=autocoupons.get(0);
		}
		return autocoupon;
	}
}
