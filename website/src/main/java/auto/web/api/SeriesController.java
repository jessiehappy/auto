package auto.web.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import auto.datamodel.BasicJson;
import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.controller.series.SeriesListResult;
import auto.datamodel.dao.Brand;
import auto.datamodel.dao.CategoryBrand;
import auto.qr.service.brand.IBrandService;
import auto.qr.service.categoryBrand.ICategoryBrandService;
import auto.qr.service.series.ISeriesService;
import auto.util.JsonUtils;
import auto.util.WebUtils;

@Controller
@RequestMapping(value = "/series")
public class SeriesController {
	
	@Autowired
	private ICategoryBrandService categoryBrandService;
	
	@Autowired
	private ISeriesService seriesService;
	
	@Autowired
	private IBrandService brandService;
	
	@RequestMapping(value = "/show/series", method = RequestMethod.POST)
	public String showSeries(HttpServletRequest request, HttpServletResponse response) {
		BasicJson result = null;
		//TODO 对code的判断
		
		Long categoryBrandId = WebUtils.getLong(request, "categoryBrandId", null);
		if (categoryBrandId == null) {
			result = new BasicJson(JsonStatus.COMMON_ID_EMPTY, JsonStatus.commonIdEmpty,null);
			return JsonUtils.toJson(result);
		}
		CategoryBrand categoryBrand = this.categoryBrandService.getCategoryBrandById(categoryBrandId);//得到类目品牌对象
		if (categoryBrand == null) {
			//不存在该商品或者该品牌在该目录下已失效
			result = new BasicJson(JsonStatus.CATEGORY_BRAND_EMPTY, JsonStatus.categoryBrandEmpty, null);
			return JsonUtils.toJson(result);
		}
		Brand brand = this.brandService.getBrandById(categoryBrand.getBrandId());
		if (brand == null) {
			//该品牌已失效
			result = new BasicJson(JsonStatus.BRAND_EMPTY, JsonStatus.brandEmpty, null);
			return JsonUtils.toJson(result);
		}
		List<SeriesListResult> items = this.seriesService.getSeriesListByBrandId(brand.getId());
		if (items == null) {
			result = new BasicJson(JsonStatus.BRAND_EMPTY, JsonStatus.brandEmpty, null);
			return JsonUtils.toJson(result);
		}
//		result = new BasicJson(JsonStatus., JsonStatus., items);
		return JsonUtils.toJson(result);
	}
}
