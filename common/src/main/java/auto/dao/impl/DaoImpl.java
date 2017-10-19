package auto.dao.impl;

import java.io.Serializable;

import auto.dao.IDao;

@SuppressWarnings("unchecked")
public class DaoImpl extends ReadonlyDaoImpl implements IDao {

    @Override
    public <T> T save(Object object) {
        T id = (T)getSession().save(object);
        deprecatedCache(object);
        updateCache(object);
        return id;
    }

    @Override
    public <T> void update(T object) {
        getSession().update(object);
        deprecatedCache(object);
        updateCache(object);
    }

    @Override
    public <T> void saveOrUpdate(T object) {
        getSession().saveOrUpdate(object);
        deprecatedCache(object);
        updateCache(object);
    }

    @Override
    public void delete(Object object) {
        getSession().delete(object);
        deprecatedCache(object);
        deleteCache(object);
    }

    @Override
    public <T> void delete(Class<T> clazz, Serializable id) {
        T o = get(clazz, id);
        delete(o);
    }

    @Override
    public void flush() {
        getSession().flush();
    }

	protected <T> void deprecatedCache(T object) {
	    //do nothing
    }

	protected <T> void updateCache(T object){
		// 更新内存数据
	}
	protected <T> void deleteCache(T object){
		//删除内存数据
	}
}
