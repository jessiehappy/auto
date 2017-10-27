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
import auto.datamodel.dao.DUser;

@Repository("masterDUserDao")
public class DUserDaoImpl extends DaoImpl implements IDUserDao {
    
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
        if(object instanceof DUser){
        	DUser user=(DUser)object;
        	cacheManager.deprecate(CacheType.id2User, user.getId());
        	List<String> usernames=new ArrayList<String>();
        	if(user.getTelephone()!=null)usernames.add(user.getTelephone());
        	if(user.getWechatId()!=null) usernames.add(user.getWechatId());
        	cacheManager.mdeprecate(CacheType.username2Id, usernames);
        }
    }

	@Override
	public DUser getDUser(String username) {
		List<DUser> users = (List<DUser>) getSession().createCriteria(DUser.class)
                .add(getUsernameCriterion(username))
                .list();
        if (users.isEmpty()) {
            return null;
        }
        DUser minUser = null;
        if (users.size() > 1) {
            for (DUser user : users) {
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
	public List<DUser> getDUsers(Collection<String> usernames) {
		if (CollectionUtils.isEmpty(usernames)) {
            return Collections.emptyList();
        }
        List<DUser> results = (List<DUser>)getSession().createCriteria(DUser.class)
                .add(getUsernameCriterion(usernames))
                .list();
        return results;
	}

	

	@Override
	public DUser createDUser(DUser user) {
		Validate.notNull(user);
		user.setModifyTime(new Date());
		save(user);
		return user;
	}
    
}
