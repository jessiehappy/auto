package auto.qr.dao.category;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.CategoryStatus;
import auto.datamodel.dao.Category;

@Repository
public class CategoryDaoImpl extends ReadonlyDaoImpl implements ICategoryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategory() {
		Criteria criteria = getSession().createCriteria(Category.class);
		criteria.add(Restrictions.eq("status", CategoryStatus.ENABLED.ordinal()));
		return criteria.list();
	}

}
