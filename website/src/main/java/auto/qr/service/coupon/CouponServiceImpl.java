package auto.qr.service.coupon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import auto.datamodel.controller.coupon.DealerCouponDetailedResult;
import auto.datamodel.controller.coupon.DealerCouponResult;
import auto.datamodel.dao.Brand;
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
@Service
public class CouponServiceImpl implements ICouponService {

	@Autowired
	@Qualifier(value = "dealerCouponDao")
	private IDealerCouponDao dealerCouponDao;
	
	@Autowired
	@Qualifier(value = "seriesDao")
	private ISeriesDao seriesDao;
	
	@Autowired
	private IBrandDao brandDao;
	
	/**
	 * 小b首页展示的每个经销商佣金最多的代金券的数量
	 */
	private static final Integer SHOW_COUPON_NUM = 3;
	
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
		Map<String, List<DealerCoupon>> dealerCouponMap = new HashMap<String, List<DealerCoupon>>();
		for(DealerCoupon dealerCoupon2 : dealerCoupon) {
			if (dealerCouponMap.containsKey(dealerCoupon2.getDealerUsername())) {
				if (dealerCouponMap.get(dealerCoupon2).size() >= SHOW_COUPON_NUM) continue;//现在暂时只展示经销商代金券佣金最多的前三个
				dealerCouponMap.get(dealerCoupon2.getDealerUsername()).add(dealerCoupon2);
				sortDealerCouponByCouponNum(dealerCouponMap.get(dealerCoupon2.getDealerUsername()));
			}
			List<DealerCoupon> newListDealerCoupons = new ArrayList<DealerCoupon>();
			newListDealerCoupons.add(dealerCoupon2);
			dealerCouponMap.put(dealerCoupon2.getDealerUsername(), newListDealerCoupons);
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
	
	private static void sortDealerCouponByCouponNum(List<DealerCoupon> list) {
		// TODO Auto-generated method stub
		Collections.sort(list, new Comparator<DealerCoupon>() {
			@Override
			public int compare(DealerCoupon o1, DealerCoupon o2) {
				if(o1.getCommission() > o2.getCommission()){
					return -1;
				}
				if(o1.getCommission() < o2.getCommission()){
					return 1;
				}
				return 0;
			}
		});
	}
	public void fillDealerCouponResult(DealerCouponResult dealerCouponResult, String code
			, List<DealerCouponDetailedResult> items) {
		dealerCouponResult.setCode(code);
		dealerCouponResult.setItems(items);
	}
	
	/*public static void main(String[] args) {
		DealerCoupon d1 = new DealerCoupon();
		DealerCoupon d2 = new DealerCoupon();
		DealerCoupon d3 = new DealerCoupon();
		d1.setCommission(1888);
		d2.setCommission(1829);
		d3.setCommission(1834);
		List<DealerCoupon> list = new ArrayList<DealerCoupon>();
		list.add(d1);
		list.add(d2);
		list.add(d3);
		sortDealerCouponByCouponNum(list);
		for (DealerCoupon dealerCoupon : list) {
			System.out.println(dealerCoupon.getCommission());
		}
	}*/
};
