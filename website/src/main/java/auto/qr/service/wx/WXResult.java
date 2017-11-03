package auto.qr.service.wx;

public class WXResult {
	
	private String session_key;  //会话密钥
	private String expires_in;
	private String openid;		//用户唯一标识
	
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
}
