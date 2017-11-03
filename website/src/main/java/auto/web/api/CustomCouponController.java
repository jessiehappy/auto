package auto.web.api;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auto.datamodel.controller.coupon.CustomCouponList;
import auto.qr.service.user.ICustomCouponService;
import auto.qr.service.user.ICustomUserService;
import auto.util.JsonUtils;
import auto.datamodel.BasicJson;
import auto.util.WebUtils;

/**
 * 小c 代金券 -控制层
 */

@CommonsLog
@Controller
@RequestMapping("/coupon/custom")
public class CustomCouponController {
	
	@Autowired
	private ICustomCouponService customCouponService;
	
    /**
     * 我的代金券 列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/mylist")
    @ResponseBody
    public String getMyCouponList(HttpServletRequest request,HttpServletResponse response){
    	BasicJson result = null;
    	String telephone=WebUtils.getNullIfEmpty(request, "telephone");
    	try {
    		List<CustomCouponList> mycoupons=customCouponService.getMycoupons(telephone);
    		result=new BasicJson(mycoupons);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
    	return JsonUtils.toJson(result);			
    }
	

}
