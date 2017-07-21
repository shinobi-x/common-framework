package cn.jixunsoft.cache.service;

import cn.jixunsoft.cache.model.RemoteLock;

/**
 * 
 *
 * @author Danfo Yam
 *
 * @date 2013年9月10日
 */
public interface RemoteLockService {

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
    public RemoteLock getLastLock(String key);

    /**
     * 更新锁
     * 
     * @param key cache key
     */
    public void updateLock(String key);

    /**
     * 加锁
     * <pre>
     * 默认释放锁时间为5分钟
     * </pre>
     * 
     * @param key cache key
     */
    public boolean lock(String key);

    /**
     * 加锁
     * @param key cache key
     * @param timeout 锁的过期时间
     * 
     * @return true为加锁成功，false为加锁失败
     */
    public boolean lock(String key, long timeout);

    /**
     * 解锁
     * 
     * @param key cache key
     */
    public void unlock(String key);

}
