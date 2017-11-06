package auto.qr.dao.series;

import java.util.List;

import auto.datamodel.dao.Series;

public interface ISeriesDao {

	Series getSeriesById(Long seriesId);

	List<Series> getSeriesByBrandId(Long id);
	
}
