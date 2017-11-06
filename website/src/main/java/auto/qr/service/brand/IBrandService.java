package auto.qr.service.brand;

import java.util.List;

import auto.datamodel.controller.coupon.CouponHomeBrandResult;
import auto.datamodel.dao.Brand;

public interface IBrandService {

	List<CouponHomeBrandResult> getCouponHomeBrand();

	Brand getBrandById(Long brandId);

}
