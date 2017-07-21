package cn.jixunsoft.cache;

import java.util.List;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.apache.log4j.Logger;

/**
 * 
 * @author Danfo Yam
 */
public class MemcachedCache implements RemoteCache<String, Object> {

    private static Logger logger = Logger.getLogger(MemcachedCache.class);
    private MemcachedClient memcachedClient;
    private Boolean isAsync = false;

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void setIsAsync(Boolean isAsync) {
        this.isAsync = isAsync;
    }

    public Object get(String key) {
        return (Object) memcachedClient.get(key);
    }

    public Map<String, Object> getKeys(List<String> keys) {
        return memcachedClient.getBulk(keys);
    }

    public Map<String, Object> getKeys(String... keys) {
        return memcachedClient.getBulk(keys);
    }

    public boolean put(String key, Object value, long timeout) {
        if (isAsync) {
            memcachedClient.set(key, (int) timeout / 1000, value);
            return true;
        }
        try {
            memcachedClient.set(key, (int) timeout / 1000, value).get();
            return true;
        } catch (Exception e) {
            logger.error(e);
        }
        return false;
    }

    public boolean replace(String key, Object value, long timeout) {
        if (isAsync) {
            memcachedClient.replace(key, (int) timeout / 1000, value);
            return true;
        }
        try {
            memcachedClient.replace(key, (int) timeout / 1000, value).get();
            return true;
        } catch (Exception e) {
            logger.error(e);
        }
        return false;
    }

    public boolean remove(String key) {
        if (isAsync) {
            memcachedClient.delete(key);
            return true;
        }
        try {
            return memcachedClient.delete(key).get();
        } catch (Exception e) {
            logger.error(e);
        }
        return false;
    }
}
