package auto.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import auto.datamodel.AuthStatus;
import auto.datamodel.BasicJson;
import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.controller.coupon.DealerCouponResult;
import auto.datamodel.dao.ProxyUser;
import auto.qr.service.coupon.ICouponService;
import auto.qr.service.user.IProxyUserService;
import auto.util.JsonUtils;
import auto.util.WebUtils;

@Controller
public class HomeController {
	
	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private IProxyUserService proxyUserService;
	
	@RequestMapping( value = "/proxy/home", method = RequestMethod.POST )
	@ResponseBody
	public String proxyHome(HttpServletRequest request, HttpServletResponse response) {
		
		BasicJson result = null;
		String code = WebUtils.getNullIfEmpty(request, "code");
		if(code == null) {
			result = new BasicJson(JsonStatus.NOT_EXIST_CODE, JsonStatus.grantCodeEmptyMsg, null);
			return JsonUtils.toJson(result);
		} 
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");
		int identifyStatus =  proxyUserService.identifyPUser(telephone);
		if (identifyStatus == AuthStatus.UNKNOWN.ordinal() 
				|| identifyStatus == AuthStatus.AUDIT.ordinal() 
				|| identifyStatus == AuthStatus.AUDIT_FAILURE.ordinal()) {
			result = new BasicJson(JsonStatus.UNVERIFIED_CODE, JsonStatus.grantCodeExpired, null);
			return JsonUtils.toJson(result);
		}
		ProxyUser proxyUser = this.proxyUserService.getPUser(telephone);
		//已经排序好
		DealerCouponResult items = this.couponService.listDealerCoupon(code, proxyUser);
		if(items == null) {
			result = new BasicJson(JsonStatus.COUPON_EMPTYM, JsonStatus.couponEmptyMsg, null);
			return JsonUtils.toJson(result);
		}
		result = new BasicJson(JsonStatus.SUCCESS_CODE, JsonStatus.successMsg, items);
		return JsonUtils.toJson(result);
	}
	/**
	 * c端用户首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping( value = "/custom/home", method = RequestMethod.POST )
	@ResponseBody
	public String customHome(HttpServletRequest request, HttpServletResponse response) {
		
		BasicJson result = null;
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");
		
		
		
		
		return JsonUtils.toJson(result);
	}
}
