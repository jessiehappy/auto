package auto.web.api;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.dao.DealerAuth;
import auto.datamodel.dao.DealerUser;
import auto.datamodel.dao.ProxyAuth;
import auto.datamodel.service.DealerUserResult;
import auto.datamodel.service.ProxyAuthResult;
import auto.util.ImageUploadUtils;
import auto.util.ImageUtils;
import auto.util.JsonUtils;
import auto.datamodel.BasicJson;
import auto.util.WebUtils;
import auto.qr.service.user.IDealerUserService;
import auto.qr.service.user.IUserService;

/**
 * 大B 提供商 -控制层
 */
@CommonsLog
@Controller
@RequestMapping("/user/dealer")
public class DealerUserController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IDealerUserService duserService;
	
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
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("picUrl", graphUrl);
			if(!graphUrl.isEmpty()){
				result=new BasicJson(map);
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
	
	
	/**
	 * 登录   方式：电话号码+密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request,HttpServletResponse response){
		BasicJson result = null;
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");
		String password = WebUtils.getNullIfEmpty(request, "password");
		
		try {
			DealerUser user=duserService.getDUser(telephone);
			if(user!=null){
				if(password.equals(user.getPassword())){
					//登录成功
					DealerUserResult dUserResult=new DealerUserResult(user);
					result=new BasicJson(dUserResult);
				}else{
					//密码错误
					result=new BasicJson(JsonStatus.USER_CODE_ERROT,JsonStatus.invalidPassword);
				}
			}else{
				//用户不存在
				result=new BasicJson(JsonStatus.USER_CODE_ERROT,JsonStatus.notExistUsername);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	/**
	 * 注册
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public String register(HttpServletRequest request,HttpServletResponse response){
		BasicJson result = null;
		String deviceId = WebUtils.getNullIfEmpty(request, "deviceId");
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");
		String phoneNum = WebUtils.getNullIfEmpty(request, "phoneNum");
		String picCode = WebUtils.getNullIfEmpty(request, "picCode");
		String password= WebUtils.getNullIfEmpty(request, "password");
		try {
			result=duserService.register(deviceId,telephone,phoneNum,picCode,password);
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	/**
	 * 重置密码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/reset",method=RequestMethod.POST)
	@ResponseBody
	public String reset(HttpServletRequest request,HttpServletResponse response){
		BasicJson result = null;
		String username = WebUtils.getNullIfEmpty(request, "username");
		String oldPS = WebUtils.getNullIfEmpty(request, "oldPS");
		String newPS= WebUtils.getNullIfEmpty(request, "newPS");
		try {
			duserService.reset(username,oldPS,newPS);
			result=new BasicJson();
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
    
	/**
	 * 更改手机号码
	 */
	@RequestMapping(value="/editPhone",method=RequestMethod.POST)
	@ResponseBody
	public String editPhone(HttpServletRequest request,HttpServletResponse response){
		BasicJson result = null;
		//账号
		String  username=WebUtils.getNullIfEmpty(request, "username");
		//新换的电话号码
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");
		//根据deviceId 获取发送的短信验证码
		String deviceId = WebUtils.getNullIfEmpty(request, "deviceId");
		//用户输入的短信验证码
		String phoneNum = WebUtils.getNullIfEmpty(request, "phoneNum");
		
		try {
			duserService.editPhone(username,telephone,deviceId,phoneNum);
			result=new BasicJson();
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	/**
	 * 更改性别
	 */
	@RequestMapping(value="/editGender")
	@ResponseBody
	public String editGender(HttpServletRequest request,HttpServletResponse response){
		BasicJson result = null;
		
		String username = WebUtils.getNullIfEmpty(request, "username");
		String gender = WebUtils.getNullIfEmpty(request, "gender");

		try {
			duserService.editGender(username,gender);
			result=new BasicJson();
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	
	/**
	 * 更改昵称
	 */
	@RequestMapping(value="/editNickname")
	@ResponseBody
	public String editNickname(HttpServletRequest request,HttpServletResponse response){
		BasicJson result = null;
		
		String username = WebUtils.getNullIfEmpty(request, "username");
		String nickName = WebUtils.getNullIfEmpty(request, "nickName");

		try {
			duserService.editNickname(username,nickName);
			result=new BasicJson();
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	
	/**
	 * 更改头像地址
	 */
	@RequestMapping(value="/editFavicon")
	@ResponseBody
	public String editFavcion(HttpServletRequest request,HttpServletResponse response){
		BasicJson result = null;
		
		String username = WebUtils.getNullIfEmpty(request, "username");
		String favicon = WebUtils.getNullIfEmpty(request, "favicon");

		try {
			duserService.editFavicon(username,favicon);
			result=new BasicJson();
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	
	/**
	 * 上传头像，返回头像地址
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadFavicon")
	@ResponseBody
	public String uploadQualification(HttpServletRequest request,HttpServletResponse response, @RequestParam("file") MultipartFile file){
		BasicJson result = null;
		try {
			//上传图片，返回图片地址
			String accessToken=ImageUploadUtils.getImageToken();
			byte[] contents=file.getBytes();
			String ext=ImageUtils.getSuffix(file.getName());
			String favicon=ImageUploadUtils.uploadDFavicon(accessToken, contents, ext);
			Map<String, String> map=new HashMap<String, String>();
			map.put("favicon", favicon);
			result=new BasicJson(map);
		} catch (Exception e) {
			log.error(e);
			result=new BasicJson(JsonStatus.ERROR, JsonStatus.ERROR_MSG);
		}
		return JsonUtils.toJson(result);
	}
	
	/**
	 * 上传头像，返回头像地址
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadBusinessLicence")
	@ResponseBody
	public String uploadBusinessLicence(HttpServletRequest request,HttpServletResponse response, @RequestParam("file") MultipartFile file){
		BasicJson result = null;
		try {
			//上传图片，返回图片地址
			String accessToken=ImageUploadUtils.getImageToken();
			byte[] contents=file.getBytes();
			String ext=ImageUtils.getSuffix(file.getName());
			String businessLicence=ImageUploadUtils.uploadBusinessLicence(accessToken, contents, ext);
			Map<String, String> map=new HashMap<String, String>();
			map.put("businessLicence", businessLicence);
			result=new BasicJson(map);
		} catch (Exception e) {
			log.error(e);
			result=new BasicJson(JsonStatus.ERROR, JsonStatus.ERROR_MSG);
		}
		return JsonUtils.toJson(result);
	}
	
	/**
	 * 提交 公司认证资格 申请
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/submitDUser")
	@ResponseBody
	public String subDUser(HttpServletRequest request,HttpServletResponse response){
		BasicJson result=null;
		String username=WebUtils.getNullIfEmpty(request, "username");
		String telephone=WebUtils.getNullIfEmpty(request, "telephone");
		String address=WebUtils.getNullIfEmpty(request, "address");
		String company=WebUtils.getNullIfEmpty(request, "company");
		String businessLicence=WebUtils.getNullIfEmpty(request, "businessLicence");
		String longitude=WebUtils.getNullIfEmpty(request, "longitude");
		String latitude=WebUtils.getNullIfEmpty(request, "latitude");
		
		try {
			DealerAuth dauth=duserService.createDAuth(username,telephone,address,company,businessLicence,
					new BigDecimal(longitude),new BigDecimal(latitude));
			result=new BasicJson(dauth);
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	
	/**
	 * 审核申请
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="auditDUser",method=RequestMethod.POST)
	@ResponseBody
	public String auditPUser(HttpServletRequest request,HttpServletResponse response){
		BasicJson result=null;
		String username=WebUtils.getNullIfEmpty(request, "username");
		String status=WebUtils.getNullIfEmpty(request, "status");
		try {
			Map<String, Object>Info=new HashMap<String, Object>();
			Info.put("status", Integer.valueOf(status));
			duserService.updateDAuthInfo(username, Info);
			result=new BasicJson();
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
}

