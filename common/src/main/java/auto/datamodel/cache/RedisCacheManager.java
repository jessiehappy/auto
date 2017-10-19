package auto.datamodel.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.apachecommons.CommonsLog;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import auto.util.RedisManager;
import auto.util.SerializeUtils;

@CommonsLog
@Component
@SuppressWarnings("unchecked")
public class RedisCacheManager implements ICacheManager {
    
    private static final String CACHE_PREFIX = "cache_";
    
    private static final int EXPIRE_TIME_IN_SECONDS = 60 * 5; 
    
    private static final int EXPIRE_FOREVER_TIME = -1;
    
    @Override
    public <T extends ICacheable> T get(CacheType type, Object key) {
        if (key == null || type == null) return null;
        try {
            String keyStr = CACHE_PREFIX + type.toString() + "_" + String.valueOf(key);
            byte[] resultBytes = RedisManager.get(keyStr.getBytes());
            T result = (T)SerializeUtils.deseialize(resultBytes, CacheType.clazzes[type.ordinal()]);
            return result;
        } catch(Exception e) {
            log.warn("get cache from redis failed. type = " + type + ", key = " + key, e);
            return null;
        }
    }

    @Override
    public <T extends ICacheable> List<T> mget(CacheType type,
            Collection<?> keys) {
        if (CollectionUtils.isEmpty(keys) || type == null) return Collections.emptyList();
        try {
            List<byte[]> keyBytes = new ArrayList<byte[]>();
            for (Object key : keys) {
                String keyStr = CACHE_PREFIX + type.toString() + "_" + String.valueOf(key);
                keyBytes.add(keyStr.getBytes());
            }
            List<byte[]> resultBytes = RedisManager.mget(keyBytes);
            List<T> results = new ArrayList<T>();
            for (byte[] bytes : resultBytes) {
                if (bytes == null) {
                    results.add(null);
                } else {
                    results.add((T)SerializeUtils.deseialize(bytes, CacheType.clazzes[type.ordinal()]));
                }
            }
            return results;
        } catch(Exception e) {
            log.warn("get cache from redis failed. type = " + type + ", keys = " + keys, e);
            List<T> results = new ArrayList<T>();
            for (int i = 0; i < keys.size(); i++) {
                results.add(null);
            }
            return results;
        }
    }

    @Override
    public boolean set(CacheType type, Object key, ICacheable value) {
        if (key == null || type == null || value == null) return false;
        try {
            byte[] keyBytes = (CACHE_PREFIX + type.toString() + "_" + String.valueOf(key)).getBytes();
            byte[] valueBytes = SerializeUtils.serialize(value);
            return RedisManager.set(keyBytes, valueBytes, EXPIRE_TIME_IN_SECONDS);
        } catch(Exception e) {
            log.warn("set cache to redis failed. type = " + type + ", key = " + key + ", value = " + value, e);
            return false;
        }
    }

    @Override
    public boolean setNullTime(CacheType type, Object key, ICacheable value) {
    		if (key == null || type == null || value == null) return false;
        try {
            byte[] keyBytes = (CACHE_PREFIX + type.toString() + "_" + String.valueOf(key)).getBytes();
            byte[] valueBytes = SerializeUtils.serialize(value);
            return RedisManager.set(keyBytes, valueBytes, EXPIRE_FOREVER_TIME);
        } catch(Exception e) {
            log.warn("set cache to redis failed. type = " + type + ", key = " + key + ", value = " + value, e);
            return false;
        }
    }
    @Override
    public boolean mset(CacheType type, Map<?, ? extends ICacheable> map) {
        if (type == null || map == null || map.size() == 0) return false;
        try {
            Map<byte[], byte[]> byteMap = new HashMap<byte[], byte[]>();
            for (Entry<?, ? extends ICacheable> entry : map.entrySet()) {
                byte[] keyBytes = (CACHE_PREFIX + type.toString() + "_" + String.valueOf(entry.getKey())).getBytes();
                byte[] valueBytes = SerializeUtils.serialize(entry.getValue());
                byteMap.put(keyBytes, valueBytes);
            }
            return RedisManager.mset(byteMap, EXPIRE_TIME_IN_SECONDS);
        } catch(Exception e) {
            log.warn("set cache to redis failed. type = " + type + ", data = " + map, e);
            return false;
        }
    }

    @Override
    public boolean deprecate(CacheType type, Object key) {
        if (key == null || type == null) return false;
        try {
            byte[] keyBytes = (CACHE_PREFIX + type.toString() + "_" + String.valueOf(key)).getBytes();
            return RedisManager.del(keyBytes);
        } catch(Exception e) {
            log.warn("deprecate cache to redis failed. type = " + type + ", key = " + key, e);
            return false;
        }
    }

    @Override
    public boolean mdeprecate(CacheType type, Collection<?> keys) {
        if (CollectionUtils.isEmpty(keys) || type == null) return false;
        try {
            List<byte[]> keyBytes = new ArrayList<byte[]>();
            for (Object key : keys) {
                keyBytes.add((CACHE_PREFIX + type.toString() + "_" + String.valueOf(key)).getBytes());
            }
            return RedisManager.mdel(keyBytes);
        } catch(Exception e) {
            log.warn("deprecate cache to redis failed. type = " + type + ", keys = " + keys, e);
            return false;
        }
    }

	@Override
	public boolean updateEntity(CacheType type, Object key, ICacheable value) {
		// TODO Auto-generated method stub
		if (key == null || type == null) return false;
		try {
            byte[] keyBytes = (CACHE_PREFIX+String.valueOf(key)).getBytes();
            byte[] valueBytes = SerializeUtils.serialize(value);
            return RedisManager.set(keyBytes, valueBytes, EXPIRE_TIME_IN_SECONDS);
        } catch(Exception e) {
            log.warn("set cache to redis failed. type = " + type + ", key = " + key + ", value = " + value, e);
            return false;
        }
	}

}
