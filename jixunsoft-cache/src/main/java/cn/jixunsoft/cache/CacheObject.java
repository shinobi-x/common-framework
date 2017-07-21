/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.jixunsoft.cache;

import java.lang.ref.SoftReference;


/**
 *
 * @author Danfo Yam
 */
public class CacheObject extends SoftReference<Object> {

    private long createTime = System.currentTimeMillis();

    private long expireTime = 5 * 60 * 1000;

    public CacheObject(Object referent) {
        super(referent);
    }

    public CacheObject(Object referent, long expireTime) {
        this(referent);
        this.expireTime = expireTime;
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
