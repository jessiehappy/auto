package auto.qr.service.user;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auto.datamodel.controller.coupon.CustomCoupon;
import auto.datamodel.controller.coupon.CustomCouponDetail;
import auto.datamodel.controller.coupon.CustomCouponList;
import auto.datamodel.dao.AutoCoupon;
import auto.datamodel.dao.Coupon;
import auto.datamodel.dao.DealerAuth;
import auto.datamodel.dao.DealerCoupon;
import auto.datamodel.dao.Series;
import auto.qr.dao.coupon.IAutoCouponDao;
import auto.qr.dao.coupon.ICouponDao;
import auto.qr.dao.coupon.IDealerCouponDao;
import auto.qr.dao.series.ISeriesDao;
import auto.qr.dao.user.IDealerAuthDao;

@CommonsLog
@Service
@Transactional
public class CustomCouponServiceImpl implements ICustomCouponService {

    @Autowired
    private ICouponDao couponDao;
    @Autowired
    private IAutoCouponDao autocouponDao;
    @Autowired
    private IDealerCouponDao dealcouponDao;
    @Autowired
    private ISeriesDao seriesDao;
    @Autowired
    private IDealerAuthDao dealerauthDao;
   
    private CustomCoupon getCustomCoupon(Coupon coupon){
    	//获取代金券的id
		Long couponId=coupon.getId();
		AutoCoupon autoCoupon=autocouponDao.getAutoCoupon(couponId);
		//获取经销商发布券的id
		Long dealerCouponId=autoCoupon.getDealerCouponId();
		DealerCoupon dealerCoupon=dealcouponDao.getDealerCouponById(dealerCouponId);
	    //获取车系id
		Long seriesId=dealerCoupon.getDealerSeriesId();
		Series series=seriesDao.getSeriesById(seriesId);
		//获取经销商用户标识username
		String dealerUserName=dealerCoupon.getDealerUsername();
		DealerAuth dAuth=dealerauthDao.getDAuth(dealerUserName);
		CustomCoupon customCoupon=new CustomCoupon(coupon, dAuth, series, dealerCoupon);
		
		return customCoupon;
    }

	@Override
	public List<CustomCouponList> getMycoupons(String username) {
		List<CustomCouponList> list=new ArrayList<CustomCouponList>();
		//根据username查询coupon表
		List<Coupon> coupons=couponDao.getCoupons(username);
		for (Coupon coupon : coupons) {
			CustomCoupon customCoupon=getCustomCoupon(coupon);			
			CustomCouponList customCoupons=new CustomCouponList(customCoupon);
			//添加
			list.add(customCoupons);
		}
		return list;
	}


	@Override
	public CustomCouponDetail getCouponDetail(Long couponId) {
		// TODO Auto-generated method stub
		return null;
	}
}
