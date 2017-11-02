package auto.qr.dao.coupon;

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
	
	
}
