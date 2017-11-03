package auto.web.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import auto.datamodel.BasicJson;
import auto.datamodel.ProxyUserIdentityType;
import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.controller.coupon.GenerateCouponResult;
import auto.datamodel.controller.coupon.OneDealerCouponResult;
import auto.datamodel.dao.ProxyUser;
import auto.qr.service.coupon.ICouponService;

import auto.qr.service.user.IProxyUserService;
import auto.util.JsonUtils;
import auto.util.WebUtils;

@Controller
@RequestMapping("/coupon")
public class CouponController {

	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private IProxyUserService proxyUserService;
	
	@Autowired
	@Qualifier(value = "masterCouponService")
	private auto.master.service.coupon.ICouponService masterCouponService;
	
	@RequestMapping(value = "/show/details", method = RequestMethod.POST)
	@ResponseBody
	public String showDetails(HttpServletRequest request, HttpServletResponse response) {
		BasicJson result = null;
		Long id = WebUtils.getLong(request, "id", null);
		if (id == null) {
			result = new BasicJson(JsonStatus.COUPONID_EMPTY, JsonStatus.couponidEmpty, null);
			return JsonUtils.toJson(result);
		}
		String code = WebUtils.getNullIfEmpty(request, "code");
		if (code == null) {
			result = new BasicJson(JsonStatus.NOT_EXIST_CODE, JsonStatus.grantCodeEmptyMsg, null);
			return JsonUtils.toJson(result);
		}
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");
		int identifyStatus =  proxyUserService.identifyPUser(telephone);
		if (identifyStatus == ProxyUserIdentityType.UNVERIFIED.ordinal() 
				|| identifyStatus == ProxyUserIdentityType.UNDERREVIEW.ordinal() 
				|| identifyStatus == ProxyUserIdentityType.NOTPASS.ordinal()) {
			result = new BasicJson(JsonStatus.UNVERIFIED_CODE, JsonStatus.grantCodeExpired, null);
			return JsonUtils.toJson(result);
		}
		ProxyUser proxyUser = this.proxyUserService.getPUser(telephone);
		OneDealerCouponResult item = this.couponService.getOneDealerCouponById(id, proxyUser);
		if (item == null) {
			result = new BasicJson(JsonStatus.COUPON_EMPTYM, JsonStatus.couponEmpty, item);
			return JsonUtils.toJson(result);
		}
		item.setCode(code);
		result = new BasicJson(JsonStatus.SUCCESS_CODE, JsonStatus.successMsg, item);
		return JsonUtils.toJson(result);
	}
	
	@RequestMapping(value = "/click/generate/coupon", method = RequestMethod.POST)
	@ResponseBody
	public String clickGenerateCoupon(HttpServletRequest request, HttpServletResponse response) {
		BasicJson result = null;
		String code = WebUtils.getNullIfEmpty(request, "code");
		if (code == null) {
			result = new BasicJson(JsonStatus.NOT_EXIST_CODE, JsonStatus.grantCodeEmptyMsg, null);
			return JsonUtils.toJson(result);
		}
		Long id = WebUtils.getLong(request, "id", null);
		if (id == null) {
			result = new BasicJson(JsonStatus.COUPONID_EMPTY, JsonStatus.couponidEmpty, null);
			return JsonUtils.toJson(result);
		}
		GenerateCouponResult data = this.couponService.getOneDealerCouponById(id);
		if (data == null) {
			result = new BasicJson(JsonStatus.DEALER_GOODS_EMPTY, JsonStatus.dealerGoodsEmpty, null);
			return JsonUtils.toJson(result);
		}
		result = new BasicJson(JsonStatus.SUCCESS_CODE, JsonStatus.successMsg, data);
		return JsonUtils.toJson(result);
	}
	
	@RequestMapping(value = "/generate/coupon", method = RequestMethod.POST)
	@ResponseBody
	public String generateCoupon(HttpServletRequest request, HttpServletResponse response) {
		BasicJson result = null;
		Long id = WebUtils.getLong(request, "id", null);//要生成的代金券主键
		if (id == null) {
			result = new BasicJson(JsonStatus.COUPONID_EMPTY, JsonStatus.couponidEmpty, null);
			return JsonUtils.toJson(result);
		}
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");//小b
		int identifyStatus =  proxyUserService.identifyPUser(telephone);
		if (identifyStatus == ProxyUserIdentityType.UNVERIFIED.ordinal() 
				|| identifyStatus == ProxyUserIdentityType.UNDERREVIEW.ordinal() 
				|| identifyStatus == ProxyUserIdentityType.NOTPASS.ordinal()) {
			result = new BasicJson(JsonStatus.UNVERIFIED_CODE, JsonStatus.grantCodeExpired, null);
			return JsonUtils.toJson(result);
		}
		ProxyUser proxyUser = this.proxyUserService.getPUser(telephone);
		Integer couponQuota = WebUtils.getInt(request, "couponQuota", null);//小b设置的代金券额度
		if (couponQuota == null) {
			result = new BasicJson(JsonStatus.COUPONQUOTA_EMPTY,JsonStatus.couponquotaEmpty,null);
			return JsonUtils.toJson(result);
		}
		Integer errCode = this.masterCouponService.generateCoupon(id, proxyUser, couponQuota);
		if (errCode == JsonStatus.GENERATE_COUPON_ERROR) {
			result = new BasicJson(JsonStatus.GENERATE_COUPON_ERROR, JsonStatus.generateCouponError, errCode);
			return JsonUtils.toJson(result);
		}
		result = new BasicJson(JsonStatus.SUCCESS, JsonStatus.successMsg, errCode);
		return JsonUtils.toJson(result);
	}
}
