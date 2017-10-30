package auto.qr.dao.coupon;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.dao.Brand;

/**
 * 品牌数据访问层
 * @author wangWentao
 *
 */
@Repository("brandDao")
public class BrandDaoImpl extends ReadonlyDaoImpl implements IBrandDao {

	@Override
	public Brand getBrandById(Long brandId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Brand.class);
		criteria.add(Restrictions.eq("id", brandId));
		Brand brand = (Brand) criteria.uniqueResult();
		return brand;
	}

}
