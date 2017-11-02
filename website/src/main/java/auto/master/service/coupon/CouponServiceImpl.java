package auto.master.service.coupon;

import java.util.Date;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import auto.datamodel.DealerCouponLockStatus;
import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.dao.DealerCoupon;
import auto.datamodel.dao.ProxyCoupon;
import auto.datamodel.dao.ProxyUser;
import auto.master.dao.coupon.ICouponDao;
import auto.qr.dao.coupon.IDealerCouponDao;

@CommonsLog
@Service("masterCouponService")
public class CouponServiceImpl implements ICouponService {
	
	@Autowired
	private IDealerCouponDao dealerCouponDao;
	
	@Autowired
	@Qualifier(value = "masterCouponDao")
	private ICouponDao couponDao;
	
	@Override
	public Integer generateCoupon(Long id, ProxyUser proxyUser, Integer couponQuota) {
		if (id == null || proxyUser == null || couponQuota == null) return null;
		DealerCoupon dealerCoupon = this.dealerCouponDao.getDealerCouponById(id);
		dealerCoupon.setLock(DealerCouponLockStatus.COUPON_LOCKED.ordinal());//设置此经销商代金券已锁定
		dealerCoupon.setLockTime(new Date().getTime());//锁定时间
		ProxyCoupon proxyCoupon = new ProxyCoupon();
		proxyCoupon.setProxyUsername(proxyUser.getUsername());
		proxyCoupon.setCoupon(couponQuota);
		proxyCoupon.setDealerCouponId(id);
		proxyCoupon.setDealerUsername(dealerCoupon.getDealerUsername());
		proxyCoupon.setProxyCommission(dealerCoupon.getCommission());
		proxyCoupon.setSeriesId(dealerCoupon.getSeriesId());
		try {
			this.couponDao.saveProxyCoupon(proxyCoupon);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonStatus.GENERATE_COUPON_ERROR;
		}
		return JsonStatus.SUCCESS;
	}

}
