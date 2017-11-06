package auto.qr.service.series;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.datamodel.controller.series.SeriesListResult;
import auto.datamodel.dao.Series;
import auto.qr.dao.series.ISeriesDao;

@Service
public class SeriesServiceImpl implements ISeriesService {

	@Autowired
	private ISeriesDao seriesDao;
	
	@Override
	public List<SeriesListResult> getSeriesListByBrandId(Long id) {
		if(id == null) return null;
		List<SeriesListResult> seriesList = new ArrayList<SeriesListResult>();
		List<Series> series = this.seriesDao.getSeriesByBrandId(id);
		return null;
	}

}
