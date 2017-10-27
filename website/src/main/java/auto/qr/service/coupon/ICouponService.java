package auto.qr.service.coupon;

import auto.datamodel.controller.coupon.DealerCouponResult;

/**
 * @author wangWentao
 *
 */
public interface ICouponService {

	DealerCouponResult listDealerCoupon(String code);

}
