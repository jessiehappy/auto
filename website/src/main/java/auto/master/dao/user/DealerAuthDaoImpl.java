package auto.master.dao.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import auto.dao.impl.DaoImpl;
import auto.datamodel.cache.CacheType;
import auto.datamodel.dao.DealerAuth;

@Repository("masterDAuthDao")
public class DealerAuthDaoImpl extends DaoImpl implements IDealerAuthDao {
    
	private Criterion getUsernameCriterion(Collection<String> usernames) {
		return Restrictions.or(
				Restrictions.in("username", usernames),
				Restrictions.in("telephone", usernames),
				Restrictions.in("company", usernames)
     );
	}
	
	private Criterion getUsernameCriterion(String username) {
		return Restrictions.or(
				Restrictions.eq("username", username),
				Restrictions.eq("telephone", username),
				Restrictions.eq("company", username)
     );
	}
	
    @Override
    protected <T> void deprecatedCache(T object) {
        if (object == null) return ;
        if(object instanceof DealerAuth){
        	DealerAuth user=(DealerAuth)object;
        	cacheManager.deprecate(CacheType.id2dealerUser, user.getId());
        	List<String> usernames=new ArrayList<String>();
        	/*if(user.getTelephone()!=null)usernames.add(user.getTelephone());
        	if(user.getWechatId()!=null) usernames.add(user.getWechatId());*/
        	cacheManager.mdeprecate(CacheType.username2DealerUser, usernames);
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public DealerAuth getDAuth(String username) {
		List<DealerAuth> users = (List<DealerAuth>) getSession().createCriteria(DealerAuth.class)
                .add(getUsernameCriterion(username))
                .list();
        if (users.isEmpty()) {
            return null;
        }
        DealerAuth minUser = null;
        if (users.size() > 1) {
            for (DealerAuth user : users) {
                if (minUser == null || minUser.getId() > user.getId()) {
                    minUser = user;
                }
            }
        } else {
            minUser = users.get(0);
        }
        return minUser;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DealerAuth> getDAuths(Collection<String> usernames) {
		if (CollectionUtils.isEmpty(usernames)) {
            return Collections.emptyList();
        }
        List<DealerAuth> results = (List<DealerAuth>)getSession().createCriteria(DealerAuth.class)
                .add(getUsernameCriterion(usernames))
                .list();
        return results;
	}

	

	@Override
	public DealerAuth createDAuth(DealerAuth user) {
		Validate.notNull(user);
		save(user);
		return user;
	}
    
}
