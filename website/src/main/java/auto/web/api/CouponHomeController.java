package auto.web.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import auto.datamodel.BasicJson;
import auto.datamodel.ProxyUserIdentityType;
import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.controller.coupon.CouponHomeBrandResult;
import auto.qr.service.brand.IBrandService;
import auto.qr.service.user.IProxyUserService;
import auto.util.JsonUtils;
import auto.util.WebUtils;

@Controller
public class CouponHomeController {
	
	@Autowired
	private IProxyUserService proxyUserService;
	
	@Autowired
	private IBrandService brandService;
	
	@RequestMapping(value = "/couponHome", method = RequestMethod.POST)
	public String couponHome(HttpServletRequest request, HttpServletResponse response) {
		BasicJson result = null;
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");//小b的电话
		int identifyStatus =  proxyUserService.identifyPUser(telephone);
		if (identifyStatus == ProxyUserIdentityType.UNVERIFIED.ordinal() 
				|| identifyStatus == ProxyUserIdentityType.UNDERREVIEW.ordinal() 
				|| identifyStatus == ProxyUserIdentityType.NOTPASS.ordinal()) {
			result = new BasicJson(JsonStatus.UNVERIFIED_CODE, JsonStatus.grantCodeExpired, null);
			return JsonUtils.toJson(result);
		}
		List<CouponHomeBrandResult> data = this.brandService.getCouponHomeBrand();
		if (data == null) {
			
		}
		return JsonUtils.toJson(result);
	}

}
