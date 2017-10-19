package auto.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		
		return "nihao ceshi";
	}
}
