package auto.qr.dao.coupon;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
				.setProjection(Projections.groupProperty("dealerUsername"))//分组
				.addOrder(Order.desc("commission"));//降序排列
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
	
	/*还可以配合Projections的groupProperty()来对结果进行分组，例如以"age"进行分组，也就是如果资料中"age"如果有 20、20、25、30，则以下会显示20、25、30：
	Criteria criteria = session.createCriteria(User.class);
	criteria.setProjection(Projections.groupProperty("age"));
	List users = criteria.list();
	Iterator iterator = users.iterator();
	while(iterator.hasNext()) {
	    System.out.println(iterator.next());      
	}
	
	Criteria可以进行复合查询，即在原有的查询基础上再进行查询，例如在Room对User的一对多关联中，在查询出所有的Room资料之后，希望再查询users中"age"为30的user资料：
	Criteria roomCriteria = session.createCriteria(Room.class);
	Criteria userCriteria = roomCriteria.createCriteria("users");
	userCriteria.add(Restrictions.eq("age", new Integer(30)));
	List rooms = roomCriteria.list(); // 只列出users属性中有user之"age"为30的Room
	Iterator iterator = rooms.iterator();*/
}
