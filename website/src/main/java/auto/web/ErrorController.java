package auto.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auto.datamodel.BasicJson;
import auto.util.JsonUtils;

/**
 * @author WangXiao
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/404")
    @ResponseBody
    public String error404() {
    		BasicJson result = new BasicJson(404, "page not found");
        return JsonUtils.toJson(result);
    }

    @RequestMapping("/500")
    @ResponseBody
    public String error500() {
    		BasicJson result = new BasicJson(500, "web server error");
        return JsonUtils.toJson(result);
    }

}
