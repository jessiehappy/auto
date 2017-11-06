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
		List<SeriesListResult> seriesListResult = new ArrayList<SeriesListResult>();
		List<Series> seriesList = this.seriesDao.getSeriesByBrandId(id);
		if(seriesList == null) return null;
		for(Series series : seriesList) {
			SeriesListResult seriesResult = new SeriesListResult(series);
			seriesListResult.add(seriesResult);
		}
		return null;
	}

}
