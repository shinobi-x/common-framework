package cn.jixunsoft.cache.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.jixunsoft.cache.manager.CacheManager;
import cn.jixunsoft.cache.model.RemoteLock;
import cn.jixunsoft.cache.service.RemoteLockService;

/**
 * 
 *
 * @author Danfo Yam
 *
 * @date 2013年9月10日
 */
@Service
public class RemoteLockServiceImpl implements RemoteLockService {

    private final static long CACHE_TIMEOUT = 30 * 24 * 60 * 60 * 1000l;

    private final static long DEFAULT_LOCK_TIMEOUT = 5 * 60 * 1000;

    /**
     * 缓存客户端
     */
    private CacheManager cacheManager;

    /**
     * @param cacheManager the cacheManager to set
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * 取得锁
     * <pre>
     * 如果锁不存在，则新增锁
     * </pre>
     * 
     * @param key cache key
     * 
     * @return 
     */
    public RemoteLock getLastLock(String key) {
        RemoteLock remoteLock = (RemoteLock) cacheManager.get(key);

        if (remoteLock == null) {
            remoteLock = new RemoteLock();
            remoteLock.setStatus(RemoteLock.STATUS_UNLOCK);
            remoteLock.setUpdateTime(0l);
            cacheManager.put(key, remoteLock, CACHE_TIMEOUT);
        }
        return remoteLock;
    }

    /**
     * 更新锁
     * 
     * @param key cache key
     */
    public void updateLock(String key) {

        RemoteLock remoteLock = (RemoteLock) cacheManager.get(key);

        if (remoteLock == null) {
            remoteLock = new RemoteLock();
            remoteLock.setStatus(RemoteLock.STATUS_UNLOCK);
        }

        remoteLock.setUpdateTime(System.currentTimeMillis());
        cacheManager.put(key, remoteLock, CACHE_TIMEOUT);
    }

    
    public boolean lock(String key) {
        return lock(key, DEFAULT_LOCK_TIMEOUT);
    }

    public boolean lock(String key, long timeout) {

        RemoteLock remoteLock = (RemoteLock) cacheManager.get(key);

        if (remoteLock == null) {
            remoteLock = new RemoteLock();
        }

        long diff = System.currentTimeMillis() - remoteLock.getUpdateTime();

        // 如果当前是锁住的，并且未超过过期时间，则返回false
        if (remoteLock.getStatus().intValue() == RemoteLock.STATUS_LOCKED && diff < timeout) {
            return false;
        }

        remoteLock.setStatus(RemoteLock.STATUS_LOCKED);
        remoteLock.setUpdateTime(System.currentTimeMillis());
        cacheManager.put(key, remoteLock, CACHE_TIMEOUT);
        return true;
    }

    public void unlock(String key) {

        RemoteLock remoteLock = (RemoteLock) cacheManager.get(key);

        if (remoteLock == null) {
            return;
        }

        remoteLock.setStatus(RemoteLock.STATUS_UNLOCK);
        remoteLock.setUpdateTime(System.currentTimeMillis());
        cacheManager.put(key, remoteLock, CACHE_TIMEOUT);
    }

}
