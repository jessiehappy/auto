package auto.qr.service.coupon;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import auto.datamodel.controller.coupon.DealerCouponDetailedResult;
import auto.datamodel.controller.coupon.DealerCouponResult;
import auto.qr.dao.coupon.IDealerCouponDao;

/**
 * 经销商 的 代金券 
 * @author wangWentao
 *
 */
public class CouponServiceImpl implements ICouponService {

	@Autowired
	@Qualifier("dealerCouponDao")
	private IDealerCouponDao dealerCouponDao;
	
	@Override
	public DealerCouponResult listDealerCoupon(String code) {//参数为经销商对象dealerUser  和code
		// TODO 再判断用户名是否为空
		DealerCouponResult dealerCouponResult = new DealerCouponResult();;
		List<DealerCouponDetailedResult> items = new ArrayList<DealerCouponDetailedResult>();
		DealerCouponDetailedResult dealerCoupon = null;
		dealerCouponResult.setCode(code);
		dealerCouponResult.setItems(items);
		return dealerCouponResult;
	}
	
}
