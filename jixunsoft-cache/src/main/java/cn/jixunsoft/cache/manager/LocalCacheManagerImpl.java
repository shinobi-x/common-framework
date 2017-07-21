/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.jixunsoft.cache.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.jixunsoft.cache.CacheObject;
import cn.jixunsoft.cache.LocalCache;

/**
 *
 * @author Danfo Yam
 */
public class LocalCacheManagerImpl implements CacheManager {

    private LocalCache<String, CacheObject> localCache;
    private long timeout = 5 * 60 * 1000;
    private Map<String, Long> keyCountMap = new ConcurrentHashMap<String, Long>(1<<20);
    private boolean isLeakOne = false;
    private boolean isOpenCache = true;

    public void setLeakOne(boolean isLeakOne) {
		this.isLeakOne = isLeakOne;
	}

	public void setLocalCache(LocalCache<String, CacheObject> localCache) {
        this.localCache = localCache;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void setOpenCache(boolean isOpenCache) {
        this.isOpenCache = isOpenCache;
    }

    public Object get(String key) {

        if (!isOpenCache) {
            return null;
        }

        CacheObject cacheObject = (CacheObject) localCache.get(key);

        if (cacheObject == null) {
            return null;
        }

        long expireTime = cacheObject.getExpireTime();
        long createTime = cacheObject.getCreateTime();
        long pastTime = System.currentTimeMillis() - createTime;

        if (pastTime > expireTime) {
        	return null;
        }

        // 剩余过期时间在15秒以内，允许一个请求穿透缓存
        if (isLeakOne && (expireTime > 30000) && ((expireTime - pastTime) < 15000)) {
        	if (!keyCountMap.containsKey(key)) {
        		synchronized (this) {
        			if (!keyCountMap.containsKey(key)) {
        				keyCountMap.put(key, createTime);
        				return null;
        			}
				}
        	}
        }

        return cacheObject.get();
    }

    public Map<String, Object> getKeys(List<String> keys) {

        if (keys == null || keys.size() == 0) {
            return Collections.emptyMap();
        }

        Map<String, Object> map = new HashMap<String, Object>(keys.size());
        for (String key : keys) {
            Object obj = get(key);
            if (obj != null) {
                map.put(key, obj);
            }
        }
        return map;
    }

    public Map<String, Object> getKeys(String... keys) {

        if (keys == null || keys.length == 0) {
            return Collections.emptyMap();
        }

        Map<String, Object> map = new HashMap<String, Object>(keys.length);
        for (String key : keys) {
            Object obj = get(key);
            if (obj != null) {
                map.put(key, obj);
            }
        }
        return map;
    }

    public void remove(String key) {

        if (!isOpenCache) {
            return;
        }

        localCache.remove(key);
        if (isLeakOne && keyCountMap.containsKey(key)) {
        	synchronized (this) {
				if (keyCountMap.containsKey(key)) {
					keyCountMap.remove(key);
				}
			}
        }
    }

    public void put(String key, Object value, long expireTime) {

        if (!isOpenCache) {
            return;
        }

        CacheObject cacheObject = new CacheObject(value, expireTime);
        localCache.put(key, cacheObject);

        if (isLeakOne && keyCountMap.containsKey(key)) {
        	synchronized (this) {
				if (keyCountMap.containsKey(key)) {
					keyCountMap.remove(key);
				}
			}
        }
    }

    public void replace(String key, Object value, long expireTime) {

        if (!isOpenCache) {
            return;
        }

        put(key, value, expireTime);
    }

    public void put(String key, Object value) {

        if (!isOpenCache) {
            return;
        }

        put(key, value, timeout);
    }

    public void replace(String key, Object value) {

        if (!isOpenCache) {
            return;
        }

        replace(key, value, timeout);
    }
}
