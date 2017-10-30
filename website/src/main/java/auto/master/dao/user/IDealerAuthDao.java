package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.DealerAuth;

public interface IDealerAuthDao extends IDao{
	
	DealerAuth getDAuth(String username);
	
	List<DealerAuth> getDAuths(Collection<String> usernames);

	DealerAuth createDAuth(DealerAuth user);
    
}
