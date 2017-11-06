package auto.qr.service.series;

import java.util.List;

import auto.datamodel.controller.series.SeriesListResult;

public interface ISeriesService {

	List<SeriesListResult> getSeriesListByBrandId(Long id);

}
