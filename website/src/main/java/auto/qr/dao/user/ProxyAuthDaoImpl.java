package auto.qr.dao.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.cache.CacheType;
import auto.datamodel.cache.PrimitiveCacheable;
import auto.datamodel.dao.ProxyAuth;

@Repository
@SuppressWarnings("unchecked")
public class ProxyAuthDaoImpl extends ReadonlyDaoImpl implements IProxyAuthDao {
	
	private Criterion getUsernameCriterion(Collection<String> usernames) {
		return Restrictions.or(
				Restrictions.in("username", usernames),
				Restrictions.in("openId", usernames),
				Restrictions.in("realName", usernames),
				Restrictions.in("idNo", usernames)
     );
	}
	
	private Criterion getUsernameCriterion(String username) {
		return Restrictions.or(
				Restrictions.eq("username", username),
				Restrictions.eq("openId", username),
				Restrictions.eq("realName", username),
				Restrictions.eq("idNo", username)
     );
	}

	@Override
    public ProxyAuth getPAuth(String username) {
          List<ProxyAuth> users = (List<ProxyAuth>) getSession().createCriteria(ProxyAuth.class)
                    .add(getUsernameCriterion(username))
                    .list();
            
            if (users.isEmpty()) {
                return null;
            }
            ProxyAuth minUser = users.get(0);
            return minUser;
    }

	@Override
	public List<ProxyAuth> getPAuths(List<String> usernames) {
        if (CollectionUtils.isEmpty(usernames)) {
            return Collections.emptyList();
        }
        //get users from cache
        List<PrimitiveCacheable> values = cacheManager.mget(CacheType.username2Id, usernames);
        List<Long> hitIds = new ArrayList<Long>();
        List<String> missUsernames = new ArrayList<String>();
        int i = 0;
        for (String username : usernames) {
            PrimitiveCacheable value = values.get(i++);
            if (value == null) {
                missUsernames.add(username);
            } else if (!value.isEmpty()) {
                Long id = value.get();
                hitIds.add(id);
            }
        }
        List<ProxyAuth> users = new ArrayList<ProxyAuth>();
        List<Long> missIds = new ArrayList<Long>();
        //get miss users from database
        
        if (!CollectionUtils.isEmpty(missUsernames)) {
            List<ProxyAuth> missUsers = (List<ProxyAuth>)getSession().createCriteria(ProxyAuth.class)
                    .add(Restrictions.in("username", missUsernames))
                    .list();
            Map<String, ProxyAuth> name2User = new HashMap<String, ProxyAuth>();
            for (ProxyAuth user : missUsers) {
                //name2User.put(user.getUsername(), user);
                //if (user.getTelephone() != null) name2User.put(user.getTelephone(), user);
            }
            Map<String, PrimitiveCacheable> username2Id = new HashMap<String, PrimitiveCacheable>();
            Map<Long, ProxyAuth> id2User = new HashMap<Long, ProxyAuth>();
            for (String username : missUsernames) {
                ProxyAuth user = name2User.get(username);
                if (user != null) {
                    username2Id.put(username, new PrimitiveCacheable(user.getId()));
                    id2User.put(user.getId(), user);
                    users.add(user);
                } else {
                    username2Id.put(username, PrimitiveCacheable.EMPTY);
                }
            }
        }
        if (!CollectionUtils.isEmpty(missIds)) {
            List<ProxyAuth> missUsers = (List<ProxyAuth>)getSession().createCriteria(ProxyAuth.class)
                    .add(Restrictions.in("id", missIds))
                    .list();
//            log.info("mysql: " + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime()) + ", class User, method getUsers is one list, list size=" + missUsers.size()); 
            Map<Long, ProxyAuth> id2User = new HashMap<Long, ProxyAuth>();
            for (ProxyAuth user : missUsers) {
                id2User.put(user.getId(), user);
            }
            for (Long id : missIds) {
                ProxyAuth user = id2User.get(id);
                if (user != null) {
                    users.add(user);
                } else {
                    id2User.put(id, ProxyAuth.EMPTY);
                }
            }
        }
        
        return users;
	}

	@Override
	public ProxyAuth getPAuth(long id) {
		// TODO Auto-generated method stub
		return null;
	}

    
}
