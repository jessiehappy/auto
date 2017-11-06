package auto.qr.dao.series;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.SeriesStatus;
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
		Criteria criteria = getSession().createCriteria(Series.class);
		criteria.add(Restrictions.eq("id", seriesId));
		Series series = (Series) criteria.uniqueResult();
		return series;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Series> getSeriesByBrandId(Long id) {
		Criteria criteria = getSession().createCriteria(Series.class);
		criteria.add(Restrictions.eq("brandId", id))
				.add(Restrictions.eq("saleStatus", SeriesStatus.HAVE_SHELVES.ordinal()));
		List<Series> series = criteria.list();
		return series.size() == 0 ? null : series;
	}
	
	
}
