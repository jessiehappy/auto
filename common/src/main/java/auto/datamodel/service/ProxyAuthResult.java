package auto.datamodel.service;

import java.math.BigDecimal;

import auto.datamodel.dao.ProxyAuth;
/**
 * 小B 认证信息 -server层返回信息
 */
public class ProxyAuthResult  {
    
    public ProxyAuth user;
    
    public ProxyAuthResult(int errorCode, String errorMsg) {
        /*this.status = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;*/
    }
    
    public String realName;//真实姓名
    public String idNo;//身份证号
    public String frontImg;//身份证正面照
    public String qualification;//资格证照
    public BigDecimal longitude;//经度
	public BigDecimal latitude;//纬度
    public int status;//认证状态  0:未认证  1:审核中  2:未通过  3:已通过
    
    //返回用户信息
    public ProxyAuthResult (ProxyAuth user){
    	this.realName=user.getRealName();
    	this.idNo=user.getIdNo();
    	this.frontImg=user.getFrontImg();
    	this.qualification=user.getQualification();
    	this.status=user.getStatus();
    	this.longitude=user.getLongitude();
    	this.latitude=user.getLatitude();
    }
    
    public ProxyAuthResult() {
		
	}
}
