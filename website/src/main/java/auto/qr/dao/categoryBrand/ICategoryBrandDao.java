package auto.qr.dao.categoryBrand;

import java.util.List;

import auto.datamodel.dao.CategoryBrand;

public interface ICategoryBrandDao {

	List<CategoryBrand> getCategoryBrandBySecondLevId(Long id);

}
