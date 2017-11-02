package auto.qr.dao.category;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.CategoryLevel;
import auto.datamodel.CategoryStatus;
import auto.datamodel.controller.constants.CategoryName;
import auto.datamodel.dao.Category;

@Repository
public class CategoryDaoImpl extends ReadonlyDaoImpl implements ICategoryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategory(Category category) {
		Criteria criteria = getSession().createCriteria(Category.class);
		criteria.add(Restrictions.eq("lev", CategoryLevel.SECOND.ordinal()))
				.add(Restrictions.eq("parentId", category.getId()));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Category getCategoryByCnameAndLev(String automobile, int ordinal) {
		Criteria criteria = getSession().createCriteria(Category.class);
		List<Category> categories = criteria.add(Restrictions.eq("cName", automobile))
				.add(Restrictions.eq("lev", ordinal)).list();
		return categories.size() == 0 ? null : categories.get(0);
	}

}
