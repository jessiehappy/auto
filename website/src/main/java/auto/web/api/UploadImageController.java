package auto.web.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.apachecommons.CommonsLog;
import auto.datamodel.BasicJson;
import auto.datamodel.controller.constants.JsonStatus;
import auto.master.service.autho.IAuthoService;
import auto.util.JsonUtils;
import auto.util.WebUtils;

/**
 * this class is not one upload from app or web, its just one image server
 * one image must upload to web server, then upload image server
 * image server upload image just return success or failed status, but not image url
 * accept sdk upload image file
 * method:post
 * save local disk. 
 * default save /xinsilu/image/
 * accessToken is image grant token, null or expired will upload failed 
 * @author wanglongtao
 *
 */

@CommonsLog
@Controller
@RequestMapping("/upload")
public class UploadImageController {

	private static final String IMAGE_PATH = "/xinsilu/image/";
	
	@Autowired
	private IAuthoService authoService;
	
	@RequestMapping(value = "/image" ,method = RequestMethod.POST)//
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("files") MultipartFile file) {
		
		String accessToken = WebUtils.getNullIfEmpty(request, "accessToken");
		String ip = authoService.getUploadIp(accessToken);
		String accessIp = WebUtils.getRealIp(request);
		if(ip == null || !accessIp.equals(ip)) {
			return JsonStatus.unGrantUpload;
		}
		String name = WebUtils.getNullIfEmpty(request, "key");
		String filePath = IMAGE_PATH + name;
		File path = new File(filePath.substring(0, filePath.lastIndexOf("/")));
		if(!path.exists()) {
			path.mkdirs();
		}
		File fileTemp = new File(filePath);
		if (!fileTemp.exists()) {  
            //创建目录  
			try {
				fileTemp.createNewFile();
			} catch (IOException e) {
				log.error(e, e);
				return JsonStatus.uploadErrorMsg;
			}  
        } 
		try {
			FileCopyUtils.copy(file.getBytes(), new FileOutputStream(fileTemp));
		} catch (IOException e) {
			log.error(e, e);
			return JsonStatus.uploadErrorMsg;
		}
		return JsonStatus.successMsg;
	}
	
	/**
	 * image server upload accessToken
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/token" ,method = RequestMethod.POST)//
	@ResponseBody
	public String getImageAccessToken(HttpServletRequest request, HttpServletResponse response) {
		BasicJson result = null;
		try {
			String ip = WebUtils.getRealIp(request);
			String token = authoService.getImageToken(ip);
			if(token == null) {
				result = new BasicJson(JsonStatus.GRANT_FAILED, JsonStatus.grantFailed);
			}
			result = new BasicJson(JsonStatus.SUCCESS_CODE, JsonStatus.successMsg, token);
		}catch (Exception e) {
			log.error(e ,e);
			result = new BasicJson(JsonStatus.ERROR, JsonStatus.businessErrorMsg);
		}
		return JsonUtils.toJson(result);
	}
	public static void main(String[] args) {
		File file = new File(IMAGE_PATH + "verify");
		if(!file.exists()) {
			file.mkdirs();
		}
	}
}
