package auto.master.service.autho;

import auto.datamodel.UserType;
import auto.datamodel.dao.CustomUser;
import auto.datamodel.dao.DealerUser;
import auto.datamodel.dao.ProxyUser;

public interface IAuthoService {

	String getUploadIp(String accessToken);

	String getImageToken(String ip);
	
	public String grant(String username, UserType type);

	public void cancleGrant(String code);
	
	public String getTokenByCode(String code);
	
	public String freshCode(String code) ;
	
	public CustomUser getCustomUserByCode(String code);
	
	public ProxyUser getProxyUserByCode(String code);
	
	public DealerUser getDealerUserByCode(String code);
	
}
