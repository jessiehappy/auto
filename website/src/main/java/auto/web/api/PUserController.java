package auto.web.api;

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

import auto.util.ImageUploadUtils;
import auto.util.ImageUtils;
import auto.datamodel.controller.constants.JsonStatus;
import auto.datamodel.dao.PAuth;
import auto.datamodel.service.PAuthResult;
import auto.util.JsonUtils;
import auto.datamodel.BasicJson;
import auto.util.WebUtils;
import auto.qr.service.user.IPUserService;
import auto.qr.service.user.IUserService;

/**
 * 小b 代理商---控制层
 */
@CommonsLog
@Controller
@RequestMapping("/user/proxy")
public class PUserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPUserService puservice;
	
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
	 * 用户是否通过认证
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="identifyPUser",method=RequestMethod.POST)
	@ResponseBody
	public String checkPUser(HttpServletRequest request,HttpServletResponse response){
		BasicJson result=null;
		String telephone=WebUtils.getNullIfEmpty(request, "telephone");
		try {
			int  status =puservice.identifyPUser(telephone);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("status", status);
			result=new BasicJson(map);
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	/**
	 * 切换用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="switchPUser",method=RequestMethod.POST)
	@ResponseBody
	public String switchPUser(HttpServletRequest request,HttpServletResponse response){
		BasicJson result=null;
		String telephone=WebUtils.getNullIfEmpty(request, "telephone");
		String openId=WebUtils.getNullIfEmpty(request, "openId");
		try {
			//切换账号
			puservice.switchPUser(telephone,openId);
			result=new BasicJson();
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	

	/**
	 * 上传身份证正面照
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadFrontImg")
	@ResponseBody
	public String uploadFrontImg(HttpServletRequest request,HttpServletResponse response, @RequestParam("file") MultipartFile file){
		BasicJson result = null;
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");

		try {
			//根据telephone,获取username作为认证信息文件夹名称
			String username=puservice.getPUser(telephone).getUsername();
			//上传图片，返回图片地址
			String accessToken=ImageUploadUtils.getImageToken();
			byte[] contents=file.getBytes();
			String ext=ImageUtils.getSuffix(file.getName());
			String frontImg=ImageUploadUtils.uploadId(username,accessToken, contents, ext);
			Map<String, String> map=new HashMap<String, String>();
			map.put("frontImg", frontImg);
			result=new BasicJson(map);
		} catch (Exception e) {
			log.error(e);
			result=new BasicJson(JsonStatus.ERROR, JsonStatus.ERROR_MSG);
		}
		
		return JsonUtils.toJson(result);
	}
	/**
	 * 上传资格证图片
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadQualification")
	@ResponseBody
	public String uploadQualification(HttpServletRequest request,HttpServletResponse response, @RequestParam("file") MultipartFile file){
		BasicJson result = null;
		String telephone = WebUtils.getNullIfEmpty(request, "telephone");

		try {
			//根据telephone,获取username作为认证信息文件夹名称
			String username=puservice.getPUser(telephone).getUsername();
			//上传图片，返回图片地址
			String accessToken=ImageUploadUtils.getImageToken();
			byte[] contents=file.getBytes();
			String ext=ImageUtils.getSuffix(file.getName());
			String qualification=ImageUploadUtils.uploadQ(username,accessToken, contents, ext);
			Map<String, String> map=new HashMap<String, String>();
			map.put("qualification", qualification);
			result=new BasicJson(map);
		} catch (Exception e) {
			log.error(e);
			result=new BasicJson(JsonStatus.ERROR, JsonStatus.ERROR_MSG);
		}
		
		return JsonUtils.toJson(result);
	}
	
	/**
	 * 提交申请
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="submitPUser",method=RequestMethod.POST)
	@ResponseBody
	public String submitPUser(HttpServletRequest request,HttpServletResponse response){
		BasicJson result=null;
		String telephone=WebUtils.getNullIfEmpty(request, "telephone");
		String realName=WebUtils.getNullIfEmpty(request, "realName");
		String idNo=WebUtils.getNullIfEmpty(request, "idNo");
		String frontImg=WebUtils.getNullIfEmpty(request, "frontImg");
		String qualification=WebUtils.getNullIfEmpty(request, "qualification");

		try {
			PAuth pauth=puservice.createPAuth(telephone,realName,idNo,frontImg,qualification);
			PAuthResult pAuthResult=new PAuthResult(pauth);
			result=new BasicJson(pAuthResult);
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
	@RequestMapping(value="auditPUser",method=RequestMethod.POST)
	@ResponseBody
	public String auditPUser(HttpServletRequest request,HttpServletResponse response){
		BasicJson result=null;
		String telephone=WebUtils.getNullIfEmpty(request, "telephone");
		String status=WebUtils.getNullIfEmpty(request, "status");
		try {
			Map<String, Object>Info=new HashMap<String, Object>();
			Info.put("status", Integer.valueOf(status));
			puservice.updatePAuthInfo(telephone, Info);
			result=new BasicJson();
		} catch (Exception e) {
			log.error(e);
		}
		return JsonUtils.toJson(result);
	}
	
	
}
	
