package auto.qr.dao.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import auto.dao.impl.ReadonlyDaoImpl;
import auto.datamodel.cache.CacheType;
import auto.datamodel.cache.PrimitiveCacheable;
import auto.datamodel.dao.ProxyUser;
import lombok.extern.apachecommons.CommonsLog;

@Repository
@SuppressWarnings("unchecked")
@CommonsLog
public class ProxyUserDaoImpl extends ReadonlyDaoImpl implements IProxyUserDao {
	
	private Criterion getUsernameCriterion(String username) {
		return Restrictions.or(
				Restrictions.eq("username", username),
				Restrictions.eq("telephone", username),
				Restrictions.eq("openId", username),
				Restrictions.eq("wechatId", username)
     );
	}
	
	@Override
	public ProxyUser getPUser(long id) {
		ProxyUser user=cacheManager.get(CacheType.proxyToken2Id, id);
		if(user==null){
			user=get(ProxyUser.class, id);
			if(user==null){
				user=ProxyUser.EMPTY;
			}
			cacheManager.set(CacheType.proxyToken2Id, id, user);
		}
		return user.isEmpty()?null:user;
	}


	@Override
    public ProxyUser getPUser(String username) {
        if (username == null) return null;
        PrimitiveCacheable value = cacheManager.get(CacheType.username2ProxyUser, username);
        if (value != null) {
            if (value.isEmpty()) {
                return null;
            }
            Long id = value.get();
            return getPUser(id);
        } else {
            List<ProxyUser> users = (List<ProxyUser>) getSession().createCriteria(ProxyUser.class)
                    .add(getUsernameCriterion(username))
                    .list();
            if (users.isEmpty()) {
                cacheManager.set(CacheType.username2ProxyUser, username, PrimitiveCacheable.EMPTY);
                return null;
            }
            ProxyUser minUser = users.get(0);
            cacheManager.set(CacheType.username2ProxyUser, username, new PrimitiveCacheable(minUser.getId()));
            cacheManager.set(CacheType.id2ProxyUser, minUser.getId(), minUser);
            return minUser;
        }
    }


	@Override
	public List<ProxyUser> getPUsers(List<String> usernames) {
        if (CollectionUtils.isEmpty(usernames)) {
            return Collections.emptyList();
        }
        //get users from cache
        List<PrimitiveCacheable> values = cacheManager.mget(CacheType.username2ProxyUser, usernames);
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
        List<ProxyUser> users = new ArrayList<ProxyUser>();
        List<Long> missIds = new ArrayList<Long>();
        if (!hitIds.isEmpty()) {
            List<ProxyUser> hitUsers = cacheManager.mget(CacheType.id2ProxyUser, hitIds);
            for (i = 0; i < hitIds.size(); i++) {
                Long id = hitIds.get(i);
                ProxyUser user = hitUsers.get(i);
                if (user == null) {
                    missIds.add(id);
                } else if (!user.isEmpty()) {
                    users.add(user);
                }
            }
        }
        //get miss users from database
        if (!CollectionUtils.isEmpty(missUsernames)) {
            List<ProxyUser> missUsers = (List<ProxyUser>)getSession().createCriteria(ProxyUser.class)
                    .add(Restrictions.in("username", missUsernames))
                    .list();
            Map<String, ProxyUser> name2User = new HashMap<String, ProxyUser>();
            for (ProxyUser user : missUsers) {
                //name2User.put(user.getUsername(), user);
                if (user.getTelephone() != null) name2User.put(user.getTelephone(), user);
            }
            Map<String, PrimitiveCacheable> username2Id = new HashMap<String, PrimitiveCacheable>();
            Map<Long, ProxyUser> id2User = new HashMap<Long, ProxyUser>();
            for (String username : missUsernames) {
                ProxyUser user = name2User.get(username);
                if (user != null) {
                    username2Id.put(username, new PrimitiveCacheable(user.getId()));
                    id2User.put(user.getId(), user);
                    users.add(user);
                } else {
                    username2Id.put(username, PrimitiveCacheable.EMPTY);
                }
            }
            cacheManager.mset(CacheType.username2ProxyUser, username2Id);
            cacheManager.mset(CacheType.id2ProxyUser, id2User);
        }
        if (!CollectionUtils.isEmpty(missIds)) {
            List<ProxyUser> missUsers = (List<ProxyUser>)getSession().createCriteria(ProxyUser.class)
                    .add(Restrictions.in("id", missIds))
                    .list();
//            log.info("mysql: " + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime()) + ", class User, method getUsers is one list, list size=" + missUsers.size()); 
            Map<Long, ProxyUser> id2User = new HashMap<Long, ProxyUser>();
            for (ProxyUser user : missUsers) {
                id2User.put(user.getId(), user);
            }
            for (Long id : missIds) {
                ProxyUser user = id2User.get(id);
                if (user != null) {
                    users.add(user);
                } else {
                    id2User.put(id, ProxyUser.EMPTY);
                }
            }
            cacheManager.mset(CacheType.id2ProxyUser, id2User);
        }
        
        return users;
	}

	@Override
	public ProxyUser getUserByToken(String token) {
		PrimitiveCacheable value = cacheManager.get(CacheType.proxyToken2Id, token);
        if (value != null) {
            if (value.isEmpty()) {
                return null;
            }
            Long id = value.get();
            return getPUser(id);
        }
        List<ProxyUser> users = (List<ProxyUser>) getSession().createCriteria(ProxyUser.class)
                .add(Restrictions.eq("token", token))
                .list();
        if (users.size() > 1) {
            log.error("duplicated token! token = " + token);
        }
        if (CollectionUtils.isEmpty(users)) {
            cacheManager.set(CacheType.proxyToken2Id, token, PrimitiveCacheable.EMPTY);
            return null;
        }
        ProxyUser user = users.get(0);
        cacheManager.set(CacheType.proxyToken2Id, token, new PrimitiveCacheable(user.getId()));
        cacheManager.set(CacheType.id2ProxyUser, user.getId(), user);
        return user;
	}
    
}
