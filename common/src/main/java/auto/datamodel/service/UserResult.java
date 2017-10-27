package auto.datamodel.service;

import auto.datamodel.dao.User;

public class UserResult  {
    
    public User user;
    
    /*public UserResult(User user) {
        this.status = true;
        this.user = user;
    }*/
    
    public UserResult(int errorCode, String errorMsg) {
        /*this.status = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;*/
    }
    
    public String telephone;//电话
    public String wechatid;//微信id
    //返回用户信息
    public UserResult (User user){
    	this.telephone=user.getTelephone();
    	this.wechatid=user.getWechatId();
    }
}
