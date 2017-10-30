package auto.master.dao.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import auto.dao.impl.DaoImpl;
import auto.datamodel.cache.CacheType;
import auto.datamodel.dao.ProxyAuth;

@Repository("masterPAuthDao")
public class ProxyAuthDaoImpl extends DaoImpl implements IProxyAuthDao {
    
	private Criterion getUsernameCriterion(Collection<String> usernames) {
		return Restrictions.or(
				Restrictions.in("username", usernames),
				Restrictions.in("realName", usernames),
				Restrictions.in("idNo", usernames)
     );
	}
	
	private Criterion getUsernameCriterion(String username) {
		return Restrictions.or(
				Restrictions.eq("username", username),
				Restrictions.eq("realName", username),
				Restrictions.eq("idNo", username)
     );
	}
	
    @Override
    protected <T> void deprecatedCache(T object) {
        if (object == null) return ;
        if(object instanceof ProxyAuth){
        	ProxyAuth user=(ProxyAuth)object;
        	cacheManager.deprecate(CacheType.id2User, user.getId());
        	List<String> usernames=new ArrayList<String>();
        	/*if(user.getTelephone()!=null)usernames.add(user.getTelephone());
        	if(user.getWechatId()!=null) usernames.add(user.getWechatId());*/
        	cacheManager.mdeprecate(CacheType.username2Id, usernames);
        }
    }

	@Override
	public ProxyAuth getPAuth(String username) {
		List<ProxyAuth> users = (List<ProxyAuth>) getSession().createCriteria(ProxyAuth.class)
                .add(getUsernameCriterion(username))
                .list();
        if (users.isEmpty()) {
            return null;
        }
        ProxyAuth minUser = null;
        if (users.size() > 1) {
            for (ProxyAuth user : users) {
                if (minUser == null || minUser.getId() > user.getId()) {
                    minUser = user;
                }
            }
        } else {
            minUser = users.get(0);
        }
        return minUser;
	}


	@Override
	public List<ProxyAuth> getPAuths(Collection<String> usernames) {
		if (CollectionUtils.isEmpty(usernames)) {
            return Collections.emptyList();
        }
        List<ProxyAuth> results = (List<ProxyAuth>)getSession().createCriteria(ProxyAuth.class)
                .add(getUsernameCriterion(usernames))
                .list();
        return results;
	}

	

	@Override
	public ProxyAuth createPAuth(ProxyAuth user) {
		Validate.notNull(user);
		user.setCreateTime(new Date());
		save(user);
		return user;
	}
    
}
