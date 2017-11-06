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
import auto.datamodel.dao.ProxyUser;

@Repository("masterPUserDao")
public class ProxyUserDaoImpl extends DaoImpl implements IProxyUserDao {
    
	private Criterion getUsernameCriterion(Collection<String> usernames) {
		return Restrictions.or(
				Restrictions.in("username", usernames),
				Restrictions.in("telephone", usernames),
				Restrictions.in("openId", usernames),
				Restrictions.in("wechatId", usernames)
     );
	}
	
	private Criterion getUsernameCriterion(String username) {
		return Restrictions.or(
				Restrictions.eq("username", username),
				Restrictions.eq("telephone", username),
				Restrictions.eq("openId", username),
				Restrictions.eq("wechatId", username)
     );
	}
	
    @Override
    protected <T> void deprecatedCache(T object) {
        if (object == null) return ;
        if(object instanceof ProxyUser){
        	ProxyUser user=(ProxyUser)object;
        	cacheManager.deprecate(CacheType.id2ProxyUser, user.getId());
        	List<String> usernames=new ArrayList<String>();
        	if(user.getTelephone()!=null)usernames.add(user.getTelephone());
        	if(user.getWechatId()!=null) usernames.add(user.getWechatId());
        	cacheManager.mdeprecate(CacheType.username2ProxyUser, usernames);
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public ProxyUser getPUser(String username) {
		List<ProxyUser> users = (List<ProxyUser>) getSession().createCriteria(ProxyUser.class)
                .add(getUsernameCriterion(username))
                .list();
        if (users.isEmpty()) {
            return null;
        }
        ProxyUser minUser = null;
        if (users.size() > 1) {
            for (ProxyUser user : users) {
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
	public List<ProxyUser> getPUsers(Collection<String> usernames) {
		if (CollectionUtils.isEmpty(usernames)) {
            return Collections.emptyList();
        }
        List<ProxyUser> results = (List<ProxyUser>)getSession().createCriteria(ProxyUser.class)
                .add(getUsernameCriterion(usernames))
                .list();
        return results;
	}

	

	@Override
	public ProxyUser createPUser(ProxyUser user) {
		Validate.notNull(user);
		user.setModifyTime(new Date());
		save(user);
		return user;
	}
    
}
