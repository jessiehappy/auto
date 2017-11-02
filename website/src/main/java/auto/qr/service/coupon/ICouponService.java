package auto.qr.service.coupon;

import auto.datamodel.controller.coupon.DealerCouponResult;
import auto.datamodel.controller.coupon.GenerateCouponResult;
import auto.datamodel.controller.coupon.OneDealerCouponResult;
import auto.datamodel.dao.ProxyUser;

/**
 * @author wangWentao
 *
 */
public interface ICouponService {

	DealerCouponResult listDealerCoupon(String code, ProxyUser proxyUser);

	OneDealerCouponResult getOneDealerCouponById(Long id, ProxyUser proxyUser);

	GenerateCouponResult getOneDealerCouponById(Long id);

}
