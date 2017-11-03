package auto.qr.service.user;

import java.util.List;
import java.util.Map;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auto.datamodel.controller.coupon.CustomCouponList;
import auto.datamodel.dao.Coupon;
import auto.datamodel.dao.CustomUser;
import auto.qr.dao.coupon.ICouponDao;
import auto.qr.dao.user.ICustomUserDao;
import auto.util.StringUtils;

@CommonsLog
@Service
@Transactional
public class CustomUserServiceImpl implements ICustomUserService {

    @Autowired
    private ICustomUserDao cuserDao;
    
    @Qualifier(value = "masterCUserService")
    @Autowired
    private auto.master.service.user.ICustomUserService cuserService;
    
    @Autowired
    private ICouponDao couponDao;

	@Override
	public CustomUser getCUser(String username) {
		return StringUtils.isEmpty(username)?null:cuserDao.getCUser(username);
	}

	@Override
	public List<CustomUser> getCUsers(List<String> usernames) {
		return cuserDao.getCUsers(usernames);
	}

	@Override
	public CustomUser getCUser(long id) {
		return cuserDao.getCUser(id);
	}

	@Override
	public CustomUser createCUser(String telephone, String wechatId,
			String wechatName, String wechatFavicon ,String openId) {
		
		return cuserService.createCUser(telephone, wechatId, wechatName, wechatFavicon ,openId);
	}
	

	@Override
	public CustomUser updateInfo(String username, Map<String, Object> Info) {
		if(Info==null || username==null ) return  null;
		return cuserService.updateInfo(username, Info);
		
	}

	@Override
	public List<CustomCouponList> getMycoupons(String username) {
		//根据username查询coupon表
		List<Coupon> coupons=couponDao.getCoupons(username);
		for (Coupon coupon : coupons) {
			System.out.println(coupon);
		}
		return null;
	}
}
