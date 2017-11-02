package auto.qr.dao.brand;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.BrandStatus;
import auto.datamodel.dao.Brand;

/**
 * 品牌数据访问层
 * @author wangWentao
 *
 */
@Repository
public class BrandDaoImpl extends ReadonlyDaoImpl implements IBrandDao {

	@Override
	public Brand getBrandById(Long brandId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Brand.class);
		criteria.add(Restrictions.eq("id", brandId));
		Brand brand = (Brand) criteria.uniqueResult();
		return brand;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Brand> getBrand() {
		Criteria criteria = getSession().createCriteria(Brand.class);
		criteria.add(Restrictions.eq("brandStatus", BrandStatus.NORMAL.ordinal()));
		List<Brand> brands = criteria.list();
		return brands;
	}

}
