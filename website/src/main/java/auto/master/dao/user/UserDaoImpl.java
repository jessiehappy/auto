package auto.master.dao.user;

import org.springframework.stereotype.Repository;

import auto.dao.impl.DaoImpl;

@Repository("masterUserDao")
public class UserDaoImpl extends DaoImpl implements IUserDao {
    
    @Override
    protected <T> void deprecatedCache(T object) {
        if (object == null) return ;
    }
    
}
