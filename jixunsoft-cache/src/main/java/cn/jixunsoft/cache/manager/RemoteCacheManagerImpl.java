package cn.jixunsoft.cache.manager;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import cn.jixunsoft.cache.RemoteCache;

/**
 * @author Danfo Yam
 */
public class RemoteCacheManagerImpl implements CacheManager {

    private RemoteCache<String, Object> remoteCache;

    private long timeout = 60 * 60 * 1000;
    
    public void setRemoteCache(RemoteCache<String, Object> remoteCache) {
        this.remoteCache = remoteCache;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void put(String key, Object result, long expireTime) {
        remoteCache.put(key, result, expireTime);
    }

    public Object get(String key) {
        return remoteCache.get(key);
    }

    public Map<String, Object> getKeys(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyMap();
        }
        return remoteCache.getKeys(keys);
    }

    public Map<String, Object> getKeys(String... keys) {
        if (keys == null || keys.length == 0) {
            return Collections.emptyMap();
        }
        return remoteCache.getKeys(keys);
    }

    public void remove(String key) {
        remoteCache.remove(key);
    }

    public void replace(String key, Object value, long expireTime) {
        remoteCache.replace(key, value, expireTime);
    }

    public void put(String key, Object value) {
        put(key, value, timeout);
    }

    public void replace(String key, Object value) {
        replace(key, value, timeout);
    }
}
