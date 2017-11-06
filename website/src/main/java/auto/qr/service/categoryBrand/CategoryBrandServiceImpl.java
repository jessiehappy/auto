package auto.qr.service.categoryBrand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.datamodel.dao.CategoryBrand;
import auto.qr.dao.categoryBrand.ICategoryBrandDao;

@Service
public class CategoryBrandServiceImpl implements ICategoryBrandService {

	@Autowired
	private ICategoryBrandDao categoryBrandDao;
	@Override
	public CategoryBrand getCategoryBrandById(Long categoryBrandId) {
		return this.categoryBrandDao.getCategoryBrand(categoryBrandId);
	}

}
