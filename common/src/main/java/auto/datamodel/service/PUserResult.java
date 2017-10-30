package auto.datamodel.service;

import auto.datamodel.dao.PUser;
/**
 * 小B 代理商 -server层返回信息
 */
public class PUserResult  {
    
    public PUser user;
    
    public PUserResult(int errorCode, String errorMsg) {
        /*this.status = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;*/
    }
    
    
    public String openId;//微信标识
    
    public String telephone;//电话
    
    public int status;//认证状态  0:未认证  1:审核中  2:未通过  3:已通过
    //返回用户信息
    public PUserResult (PUser user){
    	this.openId=user.getOpenId();
    	this.telephone=user.getTelephone();
    	this.status=user.getStatus();
    	
    }
    
    public PUserResult() {
		
	}
}
