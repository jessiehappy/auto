package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.DAuth;

public interface IDAuthDao extends IDao{
	
	DAuth getDAuth(String username);
	
	List<DAuth> getDAuths(Collection<String> usernames);

	DAuth createDAuth(DAuth user);
    
}
