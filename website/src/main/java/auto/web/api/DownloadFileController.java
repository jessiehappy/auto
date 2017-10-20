package auto.web.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.apachecommons.CommonsLog;
import auto.datamodel.BasicJson;
import auto.datamodel.controller.constants.JsonStatus;
import auto.util.JsonUtils;

@Controller
@CommonsLog
@RequestMapping("/img")
public class DownloadFileController {

	private static final String IMAGE_PATH = "/xinsilu/image/";
	@RequestMapping(value = "/{fileName}.{ext}" ,method = RequestMethod.GET)//
	@ResponseBody
	public String upload0(HttpServletRequest request, HttpServletResponse response,  @PathVariable String fileName,@PathVariable String ext) {
		
		return upload3(request, response, null, null, null, fileName, ext);
	}
	@RequestMapping(value = "/{p1}/{fileName}.{ext}" ,method = RequestMethod.GET)//
	@ResponseBody
	public String upload1(HttpServletRequest request, HttpServletResponse response,  @PathVariable String p1, @PathVariable String fileName ,@PathVariable String ext) {
		
		return upload3(request, response, null, null, p1, fileName, ext);
	}
	
	@RequestMapping(value = "/{p2}/{p1}/{fileName}.{ext}" ,method = RequestMethod.GET)//
	@ResponseBody
	public String upload2(HttpServletRequest request, HttpServletResponse response, @PathVariable String p2, @PathVariable String p1, @PathVariable String fileName ,@PathVariable String ext) {
		
		return upload3(request, response, null, p2, p1, fileName, ext);
	}
	
	@RequestMapping(value = "/{p3}/{p2}/{p1}/{fileName}.{ext}" ,method = RequestMethod.GET)//
	@ResponseBody
	public String upload3(HttpServletRequest request, HttpServletResponse response, @PathVariable String p3 ,@PathVariable String p2, @PathVariable String p1, @PathVariable String fileName ,@PathVariable String ext) {
		
		String path = "";
		if(p3 != null) {
			path += p3 +"/";
		}
		if(p2 != null) {
			path += p2 + "/";
		}
		if(p1 != null) {
			path += p1 + "/";
		}
		path += fileName + "." + ext;
		try {
			File file = new File(IMAGE_PATH + path);
			if(!file.exists()) {
				return JsonUtils.toJson(new BasicJson(JsonStatus.NOT_EXIT_RESOURCE, JsonStatus.notExitResource));
			}
			FileInputStream fin = new FileInputStream(file);
			int i = fin.available();
			byte data[]=new byte[i]; 
			fin.read(data); //读数据 
			fin.close(); 
			OutputStream out = response.getOutputStream();
			out.write(data);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e,e);
			return JsonUtils.toJson(new BasicJson(JsonStatus.BUSINESS_ERROR_CODE, JsonStatus.businessErrorMsg));
		}
		return null;
	}
}
