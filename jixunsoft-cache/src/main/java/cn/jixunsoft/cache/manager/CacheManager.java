package cn.jixunsoft.cache.manager;

import java.util.List;
import java.util.Map;

/**
 * @author Danfo Yam
 */
public interface CacheManager {

    public Object get(String key);

    public Map<String, Object> getKeys(List<String> keys);

    public Map<String, Object> getKeys(String... keys);

    public void remove(String key);

    public void put(String key, Object value, long expireTime);

    public void replace(String key, Object value, long expireTime);

    public void put(String key, Object value);

    public void replace(String key, Object value);
}
