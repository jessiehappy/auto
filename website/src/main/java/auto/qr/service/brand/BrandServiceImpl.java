package auto.qr.service.brand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.datamodel.CategoryLevel;
import auto.datamodel.controller.constants.CategoryName;
import auto.datamodel.controller.coupon.CouponHomeBrandDetailedResult;
import auto.datamodel.controller.coupon.CouponHomeBrandResult;
import auto.datamodel.dao.Brand;
import auto.datamodel.dao.Category;
import auto.datamodel.dao.CategoryBrand;
import auto.qr.dao.brand.IBrandDao;
import auto.qr.dao.category.ICategoryDao;
import auto.qr.dao.categoryBrand.ICategoryBrandDao;

@Service
public class BrandServiceImpl implements IBrandService {

	@Autowired
	private IBrandDao brandDao;
	
	@Autowired
	private ICategoryDao categoryDao;
	
	@Autowired
	private ICategoryBrandDao categoryBrandDao;
	
	@Override
	public List<CouponHomeBrandResult> getCouponHomeBrand() {
//		List<Brand> brands = this.brandDao.getBrand();
		Category category = this.categoryDao.getCategoryByCnameAndLev(CategoryName.AUTOMOBILE, CategoryLevel.FIRST.ordinal());//得到一级类目的汽车的类目对象
		if(category == null) return null;
		List<Category> categories = this.categoryDao.getCategory(category);//得到一级目录为汽车的二级目录(进口车、国产车等)
		if(categories == null) return null;
		sortCategory(categories);
		Map<String, List<CouponHomeBrandDetailedResult>> map2Brand = new HashMap<String, List<CouponHomeBrandDetailedResult>>();
		for(Category category2 : categories) {
			List<CouponHomeBrandDetailedResult> items = new ArrayList<CouponHomeBrandDetailedResult>();
			List<CategoryBrand> categoryBrandList = this.categoryBrandDao.getCategoryBrandBySecondLevId(category2.getId());
			for(CategoryBrand categoryBrand : categoryBrandList ) {
				Brand brand = this.brandDao.getBrandById(categoryBrand.getBrandId());
				CouponHomeBrandDetailedResult item = new CouponHomeBrandDetailedResult();
				item.setId(brand.getId());
				item.setImgUrl(brand.getBrandLogoUrl());
				item.setBrandName(brand.getBrandName());
				item.setSortNumber(categoryBrand.getSortNumber());
				items.add(item);
			}
			sortBrand(items);
			map2Brand.put(category2.getcName(), items);
		}
		List<CouponHomeBrandResult> results = new ArrayList<CouponHomeBrandResult>();
		for(Map.Entry<String, List<CouponHomeBrandDetailedResult>> items : map2Brand.entrySet()) {
			CouponHomeBrandResult result = new CouponHomeBrandResult();
			result.setCategory(items.getKey());
			result.setItems(items.getValue());
			results.add(result);
		}
		return null;
	}

	private void sortCategory(List<Category> categories) {
		Collections.sort(categories, new Comparator<Category>() {

			@Override
			public int compare(Category o1, Category o2) {
				if(o1.getSortNumber() > o2.getSortNumber()){
					return 1;
				}
				if(o1.getSortNumber() < o2.getSortNumber()){
					return -1;
				}
				
				return 0;
			}
		});
	}

	private void sortBrand(List<CouponHomeBrandDetailedResult> items) {

		Collections.sort(items, new Comparator<CouponHomeBrandDetailedResult>() {

			@Override
			public int compare(CouponHomeBrandDetailedResult o1, CouponHomeBrandDetailedResult o2) {
				if(o1.getSortNumber() > o2.getSortNumber()){
					return 1;
				}
				if(o1.getSortNumber() < o2.getSortNumber()){
					return -1;
				}
				
				return 0;
			}
		});
	}
	
	/*@Override
	public Category test() {
		Category category = this.categoryDao.getCategoryByCnameAndLev(CategoryName.AUTOMOBILE, CategoryLevel.FIRST.ordinal());
		return category;
	}*/
}
