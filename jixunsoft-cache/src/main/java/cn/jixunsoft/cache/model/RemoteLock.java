package cn.jixunsoft.cache.model;

import java.io.Serializable;

/**
 * @author zhuww
 * 
 * @date 2013-08-23 13:45:49
 */
public class RemoteLock implements Serializable {

    /**
     * Serial ID
     */
    private static final long serialVersionUID = 6932406353425925222L;

    public static int STATUS_LOCKED = 1;

    public static int STATUS_UNLOCK = 0;

    /**
     * 状态
     */
    private Integer status = 0;

    /**
     * 更新时间
     */
    private Long updateTime = 0l;

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the updateTime
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }


}
