package auto.dao;

import java.io.Serializable;

/**
 * @author yanghao
 */
public interface IDao extends IReadonlyDao {

    <T> T save(Object object);

    <T> void update(T object);

    <T> void saveOrUpdate(T object);

    void delete(Object object);

    <T> void delete(Class<T> clazz, Serializable id);
        
    void flush();

}
