package auto.datamodel;

import auto.datamodel.controller.constants.JsonStatus;

/**
 * API return json struct 
 * @author wanglongtao
 *
 */
public class BasicJson {

	public Integer errCode;
	public String msg;
	public Object data;
	
	public static BasicJson SUCCESS = new BasicJson();
	
	public static BasicJson EXCEPTION_ERROR = new BasicJson(JsonStatus.ERROR, JsonStatus.unkownError);
	
	public BasicJson() {
		this.errCode = JsonStatus.SUCCESS_CODE;
		this.msg = JsonStatus.successMsg;
	}
	
	public BasicJson(Object data) {
		this.errCode = JsonStatus.SUCCESS_CODE;
		this.msg = JsonStatus.successMsg;
		this.data = data;
	}
	
	public BasicJson(Integer errCode, String msg) {
		this.errCode = errCode;
		this.msg = msg;
	}
	
	public BasicJson(Integer errCode, String msg, Object data) {
		this.errCode = errCode;
		this.msg = msg; 
		this.data = data;
	}
}
