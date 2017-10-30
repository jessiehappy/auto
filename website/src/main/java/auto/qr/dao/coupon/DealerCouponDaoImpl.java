package auto.qr.dao.coupon;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
		criteria.add(Restrictions.eq("status", 0))
				.add(Restrictions.eq("lock", 1))
				.addOrder(Order.desc("commission"));//降序排列
		List<DealerCoupon> dealerCoupon = criteria.list();
		return dealerCoupon;
	}
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		DealerCouponDaoImpl dealerCouponDao = (DealerCouponDaoImpl) context.getBean("dealerCouponDao");
		List<DealerCoupon> list = dealerCouponDao.listDealerCoupon();
		for (DealerCoupon dealerCoupon : list) {
			System.out.println(dealerCoupon);
		}
	}
}
