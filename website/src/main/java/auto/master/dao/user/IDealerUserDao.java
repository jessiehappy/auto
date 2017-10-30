package auto.master.dao.user;

import java.util.Collection;
import java.util.List;

import auto.dao.IDao;
import auto.datamodel.dao.DealerUser;

public interface IDealerUserDao extends IDao{
	
	DealerUser getDUser(String username);
	
	List<DealerUser> getDUsers(Collection<String> usernames);

	DealerUser createDUser(DealerUser user);
    
}
