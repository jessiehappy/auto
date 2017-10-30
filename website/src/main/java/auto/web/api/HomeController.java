package auto.web.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import auto.datamodel.BasicJson;
import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.controller.coupon.DealerCouponResult;
import auto.datamodel.dao.DealerUser;
import auto.qr.service.coupon.ICouponService;
import auto.qr.service.user.IUserService;
import auto.util.JsonUtils;
import auto.util.WebUtils;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping( value = "/", method = RequestMethod.POST )
	public String dealerHome(HttpServletRequest request, HttpServletResponse response) {
		//TODO 判断小b是否认证
		String proxyUser = WebUtils.getNullIfEmpty(request, "proxyUser");
		//以下操作是认证通过的前提下进行
		BasicJson result = null;
		String code = WebUtils.getNullIfEmpty(request, "code");
		if(code == null) {
			result = new BasicJson(JsonStatus.NOT_EXIST_CODE, JsonStatus.grantCodeEmptyMsg, null);
			return JsonUtils.toJson(result);
		} 
		//已经排序好
		DealerCouponResult items = this.couponService.listDealerCoupon(code);
		if(items == null) {
			result = new BasicJson(JsonStatus.COUPON_EMPTYM, JsonStatus.couponEmptyMsg, null);
			return JsonUtils.toJson(result);
		}
		result = new BasicJson(JsonStatus.SUCCESS_CODE, JsonStatus.successMsg, items);
		return JsonUtils.toJson(result);
	}
}
