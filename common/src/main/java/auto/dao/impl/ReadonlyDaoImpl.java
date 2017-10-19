package auto.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Setter;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import auto.dao.IReadonlyDao;
import auto.datamodel.cache.ICacheManager;

@SuppressWarnings("unchecked")
public class ReadonlyDaoImpl implements IReadonlyDao {
    
    @Autowired
    @Setter
    protected SessionFactory sessionFactory;
    
    @Autowired
    protected ICacheManager cacheManager;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public <T> int getCount(Class<T> clazz, Date start) {
        Criteria criteria = getSession().createCriteria(clazz);
        if (start != null) {
            criteria.add(Restrictions.ge("createdTime", start));
        }
//        log.info("mysql: "+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime())+", method getCount,class "+clazz.getSimpleName()+", params Date=" + start);
        return ((Number) criteria.setProjection(Projections.count("id"))
                .uniqueResult()).intValue();
    }

    @Override
    public <T> T get(Class<T> clazz, Serializable id) {
        T result = (T) getSession().get(clazz, id);
//        log.info("mysql: "+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime()) +", method get ,class" +clazz.getSimpleName() + ",params ID=" + id);
        return result;
    }
    
    @Override
    public <T> T get(Class<T> clazz, String key, Object value) {
        List<T> results = getSession().createCriteria(clazz).add(Restrictions.eq(key, value)).list();
        if (results.size() > 0) {
//        	log.info("mysql:" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime()) +", method get one list ,class" + clazz.getSimpleName() + ",params key=" + key + ",value=" + value + ",result size " + results.size());
            return results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return getSession().createCriteria(clazz).list();
    }
    
    @Override
    public void evict(Object object) {
        getSession().evict(object);
    }

}
