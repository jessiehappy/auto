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
import auto.util.JsonUtils;
import auto.datamodel.BasicJson;
import auto.util.WebUtils;
import auto.qr.service.user.IUserService;

@CommonsLog
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request,HttpServletResponse response){
		BasicJson result = null;
		String deviceId = WebUtils.getNullIfEmpty(request, "deviceId");
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");
		String phoneNum = WebUtils.getNullIfEmpty(request, "phoneNum");
		String picCode = WebUtils.getNullIfEmpty(request, "picCode");
		String wechatId = WebUtils.getNullIfEmpty(request, "wechatId");
		String wechatName = WebUtils.getNullIfEmpty(request, "wechatName");
		String wechatFavicon = WebUtils.getNullIfEmpty(request, "wechatFavicon");
		
		try {
			result=userService.login(deviceId,telephone,phoneNum,picCode, wechatId, wechatName, wechatFavicon);
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
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
		String deviceId = WebUtils.getNullIfEmpty(request, "deviceId");
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");
		try {	
			boolean flag=userService.sendPhoneNum(telephone, deviceId);
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
	 * 发送验证图片
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/sendGraphCode",method=RequestMethod.POST)
	@ResponseBody
	public String sendGraphCode(HttpServletRequest  request,HttpServletResponse response){
		BasicJson result = null;
		String deviceId = WebUtils.getNullIfEmpty(request, "deviceId");
		try {	
			String graphUrl=userService.sendGraphCode(deviceId);
			if(!graphUrl.isEmpty()){
				result=new BasicJson(graphUrl);
			}else{
				result=new BasicJson(JsonStatus.DEVICEID_CODE_EMPTY,JsonStatus.deviceIdEmptyMsg);
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
		String deviceId = WebUtils.getNullIfEmpty(request, "deviceId");
		String phoneNum = WebUtils.getNullIfEmpty(request, "phoneNum");
		try {	
			boolean flag=userService.verifyPhoneNum(deviceId, phoneNum);
			if(flag){
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
	 * 图片验证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="verifyPicCode",method=RequestMethod.POST)
	@ResponseBody
	public String verifyPicCode(HttpServletRequest request,HttpServletResponse response){
		BasicJson result = null;
		String deviceId = WebUtils.getNullIfEmpty(request, "deviceId");
		String picCode = WebUtils.getNullIfEmpty(request, "PicCode");
		try {	
			boolean flag=userService.verifyPicCode(deviceId, picCode);
			if(flag){
				result=new BasicJson();
			}else{
				result=new BasicJson(JsonStatus.PICCODE_CODE_ERROR, JsonStatus.piccode_msg_error);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	
	

}
