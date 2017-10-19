package auto.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author yanghao
 */
public interface IReadonlyDao {
    
    int STATUS_OK = 0;

    int STATUS_DELETED = -1;

    /**
     * @param start
     *            included, null for all count
     */
    <T> int getCount(Class<T> clazz, Date start);

    <T> List<T> getAll(Class<T> clazz);

    <T> T get(Class<T> clazz, Serializable id);
    
    <T> T get(Class<T> clazz, String key, Object value);
    
    void evict(Object object);
    
}
