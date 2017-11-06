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

import auto.datamodel.BrandStatus;
import auto.datamodel.DealerCouponStatus;
import auto.datamodel.SeriesStatus;
import auto.datamodel.controller.coupon.DealerCouponDetailedResult;
import auto.datamodel.controller.coupon.DealerCouponResult;
import auto.datamodel.controller.coupon.GenerateCouponResult;
import auto.datamodel.controller.coupon.OneDealerCouponResult;
import auto.datamodel.dao.Brand;
import auto.datamodel.dao.DealerAuth;
import auto.datamodel.dao.DealerCoupon;
import auto.datamodel.dao.ProxyCoupon;
import auto.datamodel.dao.ProxyUser;
import auto.datamodel.dao.Series;
import auto.master.dao.user.IDealerAuthDao;
import auto.qr.dao.brand.IBrandDao;
import auto.qr.dao.coupon.IDealerCouponDao;
import auto.qr.dao.coupon.IProxyCouponDao;
import auto.qr.dao.series.ISeriesDao;

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
	
	@Autowired
	private IProxyCouponDao proxyCouponDao;
	
	@Autowired
	private IDealerAuthDao dealerAuthDao;
	/**
	 * 小b首页展示的每个经销商佣金最多的代金券的数量
	 */
	private static final Integer SHOW_COUPON_NUM = 3;
	
	@Override
	public DealerCouponResult listDealerCoupon(String code, ProxyUser proxyUser) {//参数为code和proxyUser对象
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
		List<ProxyCoupon> listProxyCoupons = this.proxyCouponDao.getProxyCouponByProxyUsername(proxyUser.getUsername());//小b的所有的代理代金券
		for(Map.Entry<String, List<DealerCoupon>> entry : dealerCouponMap.entrySet()) {
			for(DealerCoupon dealerCoupon2 : entry.getValue()) {
				Series series = this.seriesDao.getSeriesById(dealerCoupon2.getSeriesId());//得到车系
				if (series.getSaleStatus() == SeriesStatus.OFF_SHELVES.ordinal()) continue;
				Brand brand = this.brandDao.getBrandById(series.getBrandId());//得到品牌
				if (brand.getBrandStatus() == BrandStatus.TERMINATED.ordinal()) continue;
				//判断已登陆的小b是否代理这个经销商代金券
				Integer couponQuota = WhetherProxy(listProxyCoupons, dealerCoupon2);
				DealerCouponDetailedResult item = new DealerCouponDetailedResult(dealerCoupon2, series, brand, couponQuota);
				items.add(item);
			}
		}
		fillDealerCouponResult(dealerCouponResult, code, items);
		return dealerCouponResult;
	}
	
	private Integer WhetherProxy(List<ProxyCoupon> listProxyCoupons, DealerCoupon dealerCoupon) {
		// TODO Auto-generated method stub
		Integer couponQuota = null;
		if (listProxyCoupons == null) return null;
		for(ProxyCoupon proxyCoupon : listProxyCoupons) {
			if (proxyCoupon.getDealerUsername().equals(dealerCoupon.getDealerUsername())) {
				if (proxyCoupon.getDealerCouponId() == dealerCoupon.getId()) {
					couponQuota = proxyCoupon.getCoupon();
					break;
				}
			}
		}
		return couponQuota;
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

	@Override
	public OneDealerCouponResult getOneDealerCouponById(Long id, ProxyUser proxyUser) {
		if (id == null) return null;
		DealerCoupon dealerCoupon = this.dealerCouponDao.getDealerCouponById(id);//通过id查到这个经销商优惠券，佣金，经销商用户名，数量，车系ID，截止时间
		DealerAuth dealerAuth = this.dealerAuthDao.getDAuth(dealerCoupon.getDealerUsername());//得到经销商公司名和详细地址
		Series series = this.seriesDao.getSeriesById(dealerCoupon.getSeriesId());
		if (series.getSaleStatus() == SeriesStatus.OFF_SHELVES.ordinal()) return null;
		Brand brand = this.brandDao.getBrandById(series.getBrandId());
		if (brand.getBrandStatus() == BrandStatus.TERMINATED.ordinal()) return null;
		List<ProxyCoupon> listProxyCoupons = this.proxyCouponDao.getProxyCouponByProxyUsername(proxyUser.getUsername());
		Integer couponQuota = WhetherProxy(listProxyCoupons, dealerCoupon);
		OneDealerCouponResult oneDealerCouponResult = new OneDealerCouponResult(dealerCoupon,series,brand,couponQuota,dealerAuth);
		return oneDealerCouponResult;
	}

	@Override
	public GenerateCouponResult getOneDealerCouponById(Long id) {
		//先判断该该代金券是否已下架
		DealerCoupon dealerCoupon = this.dealerCouponDao.getDealerCouponById(id);
		if (dealerCoupon.getStatus() == DealerCouponStatus.COUPON_INVALID.ordinal()) return null;
		Series series = this.seriesDao.getSeriesById(dealerCoupon.getSeriesId());//车系
		if (series.getSaleStatus() == SeriesStatus.OFF_SHELVES.ordinal()) return null;
		Brand brand = this.brandDao.getBrandById(series.getBrandId());
		if (brand.getBrandStatus() == BrandStatus.TERMINATED.ordinal()) return null;
		GenerateCouponResult generateCouponResult = new GenerateCouponResult(dealerCoupon, series, brand);
		return generateCouponResult;
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
	public List<DealerCoupon> listDealerCoupons() {
		return this.dealerCouponDao.listDealerCoupon();
	}
};
