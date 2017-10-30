package auto.qr.dao.coupon;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.dao.DealerCoupon;

@Repository("dealerCouponDao")
public class DealerCouponDaoImpl extends ReadonlyDaoImpl implements IDealerCouponDao {

	/**
	 * 生效的优惠券
	 */
	private static final Integer COUPON_EFFECTIVE = 0;
	/**
	 * 未锁定的优惠券
	 */
	private static final Integer COUPON_UNLOCKED = 1;

	@SuppressWarnings("unchecked")
	@Override
	public List<DealerCoupon> listDealerCoupon() {
		Criteria criteria = getSession().createCriteria(DealerCoupon.class);
		criteria.add(Restrictions.eq("status", COUPON_EFFECTIVE))
				.add(Restrictions.eq("lock", COUPON_UNLOCKED));
//				.addOrder(Order.desc("commission"));//降序排列
		List<DealerCoupon> dealerCoupon = criteria.list();
		return dealerCoupon;
	}
	/*public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		DealerCouponDaoImpl dealerCouponDao = (DealerCouponDaoImpl) context.getBean("dealerCouponDao");
		List<DealerCoupon> list = dealerCouponDao.listDealerCoupon();
		for (DealerCoupon dealerCoupon : list) {
			System.out.println(dealerCoupon);
		}
	}*/
}
