package auto.qr.dao.coupon;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.CouponStatus;
import auto.datamodel.dao.Coupon;

@Repository
@SuppressWarnings("unchecked")
public class CouponDaoImpl extends ReadonlyDaoImpl implements ICouponDao {

	private Criterion getUsernameCriterion(Collection<String> usernames) {
		return Restrictions.or(
				Restrictions.in("username", usernames),
				Restrictions.in("telephone", usernames)
				);
	}

	private Criterion getUsernameCriterion(String username) {
		return Restrictions.or(
				Restrictions.eq("username", username),
				Restrictions.eq("telephone", username)
				);
	}

	@Override
	public Coupon getCoupon(long id) {
		Coupon coupon = (Coupon) getSession().createCriteria(Coupon.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		return coupon == null ? null : coupon;
	}

	@Override
	public Coupon getCoupon(String username) {
		if(username == null) return null;
		List<Coupon> coupons = (List<Coupon>) getSession()
				.createCriteria(Coupon.class)
				//条件
				.add(getUsernameCriterion(username))
				//优惠券状态：未使用
				.add(Restrictions.eq("status", CouponStatus.UNUSED.ordinal()))
				.list();
		Coupon coupon = coupons.get(0);
		return coupon;
	}

	@Override
	public List<Coupon> getCoupons(String username) {
		if(username == null) return null;
		List<Coupon> results = (List<Coupon>) getSession()
				.createCriteria(Coupon.class)
				//条件
				.add(getUsernameCriterion(username))
				//优惠券状态：未使用
				.add(Restrictions.eq("status", CouponStatus.UNUSED.ordinal()))
				.list();
		return results;
	}

}
