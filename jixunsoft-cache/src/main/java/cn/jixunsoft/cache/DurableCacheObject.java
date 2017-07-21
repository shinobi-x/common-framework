package cn.jixunsoft.cache;

/**
 * 持久缓存对象
 * 
 * @author Danfo Yam
 *
 * 2014年3月12日
 *
 */
public class DurableCacheObject {

    private long createTime = System.currentTimeMillis();

    private long expireTime = 5 * 60 * 1000;

    private Object object;

    public DurableCacheObject(Object object) {
        this.object = object;
    }

    public DurableCacheObject(Object object, long expireTime) {
        this.object = object;
    }

    public Object get() {
        return this.object;
    }

    /**
     * @return the expireTime
     */
    public long getExpireTime() {
        return expireTime;
    }

    /**
     * @param expireTime the expireTime to set
     */
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * @return the createTime
     */
    public long getCreateTime() {
        return createTime;
    }
}
