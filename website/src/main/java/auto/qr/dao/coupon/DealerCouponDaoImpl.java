package auto.qr.dao.coupon;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.dao.DealerCoupon;

@Repository("dealerCouponDao")
public class DealerCouponDaoImpl extends ReadonlyDaoImpl implements IDealerCouponDao {

	/**查询的是经销商的有效的未锁定的优惠券
	 * @see auto.qr.dao.coupon.IDealerCouponDao#listDealerCouponByUsername(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DealerCoupon> listDealerCoupon() {
		Criteria criteria = getSession().createCriteria(DealerCoupon.class);
		//根据dealerUsername进行分组查询    怎么写这个sql语句
		//在这里不能限定只查询出每个大B的佣金最多的前三张优惠券？？？
		criteria.add(Restrictions.eq("status", 0))
				.add(Restrictions.eq("lock", 1))
				.addOrder(Order.desc("commission"))
				.setMaxResults(3);
		List<DealerCoupon> dealerCoupon = criteria.list();
		return dealerCoupon;
	}
	public static void main(String[] args) {
		DealerCouponDaoImpl t = new DealerCouponDaoImpl();
		List<DealerCoupon> tests = t.listDealerCoupon();
		for (DealerCoupon dealerCoupon : tests) {
			System.out.println(dealerCoupon);
		}
	}
}
