package cn.jixunsoft.cache.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * JCS缓存管理器
 * 
 * @author Danfo Yam
 *
 * 2014年3月13日
 *
 */
public class JCSCacheManagerImpl implements CacheManager, InitializingBean {

    private static Logger logger = Logger.getLogger(JCSCacheManagerImpl.class);

    private JCS jcsCache;
    private int printStatInterval = 30 * 60 * 1000;
    private Map<String, Long> keyCountMap = new ConcurrentHashMap<String, Long>(1<<20);
    private boolean isLeakOne = false;

    /**
     * leakone功能的泄漏时间，单位:秒
     */
    private int leakLeftTime = 15;

    /**
     * @param printStatInterval the printStatInterval to set
     */
    public void setPrintStatInterval(int printStatInterval) {
        this.printStatInterval = printStatInterval;
    }

    /**
     * @param jcsCache the jcsCache to set
     */
    public void setJcsCache(JCS jcsCache) {
        this.jcsCache = jcsCache;
    }

    public void setLeakOne(boolean isLeakOne) {
        this.isLeakOne = isLeakOne;
    }

    /**
     * @param leakLeftTime the leakLeftTime to set
     */
    public void setLeakLeftTime(int leakLeftTime) {
        this.leakLeftTime = leakLeftTime;
    }

    @Override
    public Object get(String key) {

        Object value = jcsCache.get(key);
        if (value == null) {
            return null;
        }

        if (isLeakOne) {
            long liveTime = jcsCache.getCacheElement(key).getElementAttributes().getTimeToLiveSeconds();
            long age = jcsCache.getCacheElement(key).getElementAttributes().getMaxLifeSeconds();
            if (age > 30 && liveTime <= leakLeftTime) {
                if (!keyCountMap.containsKey(key)) {
                    synchronized (this) {
                        if (!keyCountMap.containsKey(key)) {
                            keyCountMap.put(key, age);
                            return null;
                        }
                    }
                }
            }
        }

        return value;
    }

    @Override
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

    @Override
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

    @Override
    public void remove(String key) {

        try {
            jcsCache.remove(key);

            if (isLeakOne && keyCountMap.containsKey(key)) {
                synchronized (this) {
                    if (keyCountMap.containsKey(key)) {
                        keyCountMap.remove(key);
                    }
                }
            }
        } catch (CacheException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    @Deprecated
    public void put(String key, Object value, long expireTime) {
        throw new RuntimeException("forbidden invoke...");
    }

    @Override
    @Deprecated
    public void replace(String key, Object value, long expireTime) {
        throw new RuntimeException("forbidden invoke...");
    }

    @Override
    public void put(String key, Object value) {

        try {
            jcsCache.put(key, value);

            if (isLeakOne && keyCountMap.containsKey(key)) {
                synchronized (this) {
                    if (keyCountMap.containsKey(key)) {
                        keyCountMap.remove(key);
                    }
                }
            }

        } catch (CacheException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void replace(String key, Object value) {
        put(key, value);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (printStatInterval == 0) {
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    logger.info(jcsCache.getStats());
                    try {
                        Thread.currentThread().sleep(printStatInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }            
        });
        thread.start();
    }

}
