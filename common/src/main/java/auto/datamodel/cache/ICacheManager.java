package auto.datamodel.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ICacheManager {
    
    public <T extends ICacheable> T get(CacheType type, Object key);
    
    public <T extends ICacheable> List<T> mget(CacheType type, Collection<?> keys);
    
    public boolean set(CacheType type, Object key, ICacheable value);
    
    public boolean mset(CacheType type, Map<?, ? extends ICacheable> map);
    
    public boolean setNullTime(CacheType type, Object key, ICacheable value);
    
    public boolean deprecate(CacheType type, Object key);
    
    public boolean mdeprecate(CacheType type, Collection<?> keys);
    
    public boolean updateEntity(CacheType type, Object key, ICacheable value);

}
