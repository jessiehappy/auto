package auto.qr.service.brand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.datamodel.controller.coupon.CouponHomeBrandDetailedResult;
import auto.datamodel.controller.coupon.CouponHomeBrandResult;
import auto.datamodel.dao.Brand;
import auto.datamodel.dao.Category;
import auto.qr.dao.brand.IBrandDao;
import auto.qr.dao.category.ICategoryDao;

@Service
public class BrandServiceImpl implements IBrandService {

	@Autowired
	private IBrandDao brandDao;
	
	@Autowired
	private ICategoryDao categoryDao;
	
	@Override
	public List<CouponHomeBrandResult> getCouponHomeBrand() {
//		List<Brand> brands = this.brandDao.getBrand();
		List<Category> categories = this.categoryDao.getCategory();//得到一级目录为汽车的二级目录
		Map<String, CouponHomeBrandDetailedResult> map2Brand = new HashMap<String, CouponHomeBrandDetailedResult>();
		return null;
	}
	
}
