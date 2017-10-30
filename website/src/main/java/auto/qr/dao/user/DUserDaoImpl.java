package auto.qr.dao.user;

import java.util.ArrayList;
import java.util.Collection;
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
import auto.datamodel.dao.DUser;

@Repository
@SuppressWarnings("unchecked")
public class DUserDaoImpl extends ReadonlyDaoImpl implements IDUserDao {
	
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
	public DUser getDUser(long id) {
		DUser user=cacheManager.get(CacheType.id2User, id);
		if(user==null){
			user=get(DUser.class, id);
			if(user==null){
				user=DUser.EMPTY;
			}
			cacheManager.set(CacheType.id2User, id, user);
		}
		return user.isEmpty()?null:user;
	}


	@Override
    public DUser getDUser(String username) {
        if (username == null) return null;
        PrimitiveCacheable value = cacheManager.get(CacheType.username2Id, username);
        if (value != null) {
            if (value.isEmpty()) {
                return null;
            }
            Long id = value.get();
            return getDUser(id);
        } else {
            List<DUser> users = (List<DUser>) getSession().createCriteria(DUser.class)
                    .add(getUsernameCriterion(username))
                    .list();
            if (users.isEmpty()) {
                cacheManager.set(CacheType.username2Id, username, PrimitiveCacheable.EMPTY);
                return null;
            }
            DUser minUser = users.get(0);
            cacheManager.set(CacheType.username2Id, username, new PrimitiveCacheable(minUser.getId()));
            cacheManager.set(CacheType.id2User, minUser.getId(), minUser);
            return minUser;
        }
    }


	@Override
	public List<DUser> getDUsers(List<String> usernames) {
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
        List<DUser> users = new ArrayList<DUser>();
        List<Long> missIds = new ArrayList<Long>();
        if (!hitIds.isEmpty()) {
            List<DUser> hitUsers = cacheManager.mget(CacheType.id2User, hitIds);
            for (i = 0; i < hitIds.size(); i++) {
                Long id = hitIds.get(i);
                DUser user = hitUsers.get(i);
                if (user == null) {
                    missIds.add(id);
                } else if (!user.isEmpty()) {
                    users.add(user);
                }
            }
        }
        //get miss users from database
        if (!CollectionUtils.isEmpty(missUsernames)) {
            List<DUser> missUsers = (List<DUser>)getSession().createCriteria(DUser.class)
                    .add(Restrictions.in("username", missUsernames))
                    .list();
            Map<String, DUser> name2User = new HashMap<String, DUser>();
            for (DUser user : missUsers) {
                //name2User.put(user.getUsername(), user);
                if (user.getTelephone() != null) name2User.put(user.getTelephone(), user);
            }
            Map<String, PrimitiveCacheable> username2Id = new HashMap<String, PrimitiveCacheable>();
            Map<Long, DUser> id2User = new HashMap<Long, DUser>();
            for (String username : missUsernames) {
                DUser user = name2User.get(username);
                if (user != null) {
                    username2Id.put(username, new PrimitiveCacheable(user.getId()));
                    id2User.put(user.getId(), user);
                    users.add(user);
                } else {
                    username2Id.put(username, PrimitiveCacheable.EMPTY);
                }
            }
            cacheManager.mset(CacheType.username2Id, username2Id);
            cacheManager.mset(CacheType.id2User, id2User);
        }
        if (!CollectionUtils.isEmpty(missIds)) {
            List<DUser> missUsers = (List<DUser>)getSession().createCriteria(DUser.class)
                    .add(Restrictions.in("id", missIds))
                    .list();
//            log.info("mysql: " + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime()) + ", class User, method getUsers is one list, list size=" + missUsers.size()); 
            Map<Long, DUser> id2User = new HashMap<Long, DUser>();
            for (DUser user : missUsers) {
                id2User.put(user.getId(), user);
            }
            for (Long id : missIds) {
                DUser user = id2User.get(id);
                if (user != null) {
                    users.add(user);
                } else {
                    id2User.put(id, DUser.EMPTY);
                }
            }
            cacheManager.mset(CacheType.id2User, id2User);
        }
        
        return users;
	}

    
}
