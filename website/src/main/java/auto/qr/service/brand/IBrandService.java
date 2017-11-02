package auto.qr.service.brand;

import java.util.List;

import auto.datamodel.controller.coupon.CouponHomeBrandResult;
import auto.datamodel.dao.Category;

public interface IBrandService {

	List<CouponHomeBrandResult> getCouponHomeBrand();

}
