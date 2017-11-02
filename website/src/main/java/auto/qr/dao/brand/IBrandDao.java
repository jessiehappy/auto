package auto.qr.dao.brand;

import java.util.List;

import auto.datamodel.dao.Brand;

public interface IBrandDao {

	Brand getBrandById(Long brandId);

	List<Brand> getBrand();

}
