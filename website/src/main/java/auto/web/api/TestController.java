package auto.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auto.datamodel.dao.Category;
import auto.qr.service.brand.IBrandService;
import auto.util.JsonUtils;

@Controller
public class TestController {
		
	@Autowired
	private IBrandService brandService;
	
	/*@RequestMapping(value = "/test")
	@ResponseBody
	public String test(){
		Category category = this.brandService.test();
		return JsonUtils.toJson(category);
	}*/
}
