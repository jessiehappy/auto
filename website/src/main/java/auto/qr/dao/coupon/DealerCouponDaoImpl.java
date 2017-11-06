package auto.qr.dao.coupon;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.DealerCouponStatus;
import auto.datamodel.dao.DealerCoupon;

@Repository("dealerCouponDao")
public class DealerCouponDaoImpl extends ReadonlyDaoImpl implements IDealerCouponDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<DealerCoupon> listDealerCoupon() {
		Criteria criteria = getSession().createCriteria(DealerCoupon.class);
		criteria.add(Restrictions.eq("status", DealerCouponStatus.COUPON_EFFECTIVE.ordinal()));
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

	@Override
	public DealerCoupon getDealerCouponById(Long id) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(DealerCoupon.class);
		criteria.add(Restrictions.eq("id", id));
		DealerCoupon dealerCoupon = (DealerCoupon) criteria.uniqueResult();
		return dealerCoupon;
	}
}
