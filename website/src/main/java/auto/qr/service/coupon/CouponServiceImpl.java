package auto.qr.service.coupon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import auto.datamodel.controller.coupon.DealerCouponDetailedResult;
import auto.datamodel.controller.coupon.DealerCouponResult;
import auto.datamodel.dao.Brand;
import auto.datamodel.dao.DUser;
import auto.datamodel.dao.DealerCoupon;
import auto.datamodel.dao.Series;
import auto.qr.dao.coupon.IBrandDao;
import auto.qr.dao.coupon.IDealerCouponDao;
import auto.qr.dao.coupon.ISeriesDao;

/**
 * 经销商 的 代金券 
 * @author wangWentao
 *
 */
public class CouponServiceImpl implements ICouponService {

	@Autowired
	@Qualifier(value = "dealerCouponDao")
	private IDealerCouponDao dealerCouponDao;
	
	@Autowired
	@Qualifier(value = "seriesDao")
	private ISeriesDao seriesDao;
	
	@Autowired
	@Qualifier(value = "brandDao")
	private IBrandDao brandDao;
	
	
	@Override
	public DealerCouponResult listDealerCoupon(String code) {//参数为code
		// TODO 再判断用户名是否为空
		DealerCouponResult dealerCouponResult = new DealerCouponResult();;
		List<DealerCouponDetailedResult> items = new ArrayList<DealerCouponDetailedResult>();
		List<DealerCoupon> dealerCoupon =  this.dealerCouponDao.listDealerCoupon();
		if (dealerCoupon == null) {
			fillDealerCouponResult(dealerCouponResult, code, items);
			return dealerCouponResult;
		}
		
		for (DealerCoupon dealerCoupon2 : dealerCoupon) {
			Series series = this.seriesDao.getSeriesById(dealerCoupon2.getSeriesId());//得到车系
			Brand brand = this.brandDao.getBrandById(series.getBrandId());//得到品牌
			
			DealerCouponDetailedResult item = new DealerCouponDetailedResult(dealerCoupon2);
			items.add(item);
		}
		fillDealerCouponResult(dealerCouponResult, code, items);
		return dealerCouponResult;
	}
	
	public void fillDealerCouponResult(DealerCouponResult dealerCouponResult, String code
			, List<DealerCouponDetailedResult> items) {
		dealerCouponResult.setCode(code);
		dealerCouponResult.setItems(items);
	}
	
};
