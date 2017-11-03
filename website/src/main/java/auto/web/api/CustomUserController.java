package auto.web.api;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import auto.datamodel.controller.constants.JsonStatus;
import auto.qr.service.user.ICustomUserService;
import auto.qr.service.user.IUserService;
import auto.qr.service.wx.WXService;
import auto.util.JsonUtils;
import auto.datamodel.BasicJson;
import auto.util.WebUtils;

/**
 * 小c 用户 -控制层
 */

@CommonsLog
@Controller
@RequestMapping("/user/custom")
public class CustomUserController {
	
	@Autowired
	private ICustomUserService cuserService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private WXService wxService;
	
	/**
	 * 发送验证短信
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/sendPhoneNum",method=RequestMethod.POST)
	@ResponseBody
	public String sendPhoneNum(HttpServletRequest  request,HttpServletResponse response){
		BasicJson result = null;
		String openId = WebUtils.getNullIfEmpty(request, "openId");
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");
		try {	
			boolean flag=userService.sendPhoneNum(telephone, openId);
			if(flag){
				result=new BasicJson();
			}else{
				result=new BasicJson(JsonStatus.INVALID_PARAMS_CODE,JsonStatus.invalidTelephone);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	
	/**
	 * 短信验证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="verifyPhoneNum",method=RequestMethod.POST)
	@ResponseBody
	public String verifyPhoneNum(HttpServletRequest request,HttpServletResponse response){
		BasicJson result = null;
		String openId = WebUtils.getNullIfEmpty(request, "openId");
		String phoneNum = WebUtils.getNullIfEmpty(request, "phoneNum");
		String telephone=WebUtils.getNullIfEmpty(request, "telephone");
		try {	
			boolean flag=userService.verifyPhoneNum(openId, phoneNum);
			if(flag){
				//验证成功，保存用户
				cuserService.createCUser(telephone, null, null, null, openId);
				result=new BasicJson();
			}else{
				result=new BasicJson(JsonStatus.PHONECODE_CODE_ERROR, JsonStatus.phonecode_msg_error);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	
	/**
	 * 根据code 获取  openId
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping("/getopenId")  
    @ResponseBody  
    public String getopenId(HttpServletRequest request, HttpServletResponse response) {     	
    	String code = WebUtils.getNullIfEmpty(request, "code");
        String openId=null; 
        try {
			openId=wxService.getOpenId(code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return openId;
    }  
	

}
