package auto.qr.dao.coupon;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.dao.Series;

/**
 * 车系的数据访问层
 * @author wangWentao
 *
 */
@Repository("seriesDao")
public class SeriesDaoImpl extends ReadonlyDaoImpl implements ISeriesDao {

	@Override
	public Series getSeriesById(Long seriesId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Series.class);
		criteria.add(Restrictions.eq("id", seriesId));
		Series series = (Series) criteria.uniqueResult();
		return series;
	}
	
	
}
