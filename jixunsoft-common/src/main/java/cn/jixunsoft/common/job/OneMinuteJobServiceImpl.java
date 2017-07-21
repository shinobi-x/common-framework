package cn.jixunsoft.common.job;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

public class OneMinuteJobServiceImpl implements OneMinuteJobService, InitializingBean {

    private static final Logger logger = Logger.getLogger(OneMinuteJobServiceImpl.class);
    
    private static final int LOG_WRITE_COUNT = 10;
    
    /**
     * 一分钟定时器任务列表，使用spring注册
     */
    private Map<String, OneMinuteJobService> oneMinuteJobMap;
    
    private int logCount = 0;

    public Map<String, OneMinuteJobService> getOneMinuteJobMap() {
        return oneMinuteJobMap;
    }

    public void setOneMinuteJobMap(Map<String, OneMinuteJobService> oneMinuteJobMap) {
        this.oneMinuteJobMap = oneMinuteJobMap;
    }

    @Override
    public void refreshCacheData() {
        
        if (MapUtils.isEmpty(oneMinuteJobMap)) {
            return;
        }

        for (Entry<String, OneMinuteJobService> oneMinuteJobEntry : oneMinuteJobMap.entrySet()) {
            
            OneMinuteJobService oneMinuteJob = oneMinuteJobEntry.getValue();
            
            try {
                oneMinuteJob.refreshCacheData();
                
                // 1分钟加载一次缓存，10分钟打印一次日志
                if (logCount >= LOG_WRITE_COUNT) {
                    logger.info("refreshCacheData for " + oneMinuteJobEntry.getKey() + " succeed.");
                }
                
            } catch (Exception e) {
                logger.error("refreshCacheData for " + oneMinuteJobEntry.getKey() + " failed.", e);
            }
        }
        
        // 重置计数器
        if (logCount >= LOG_WRITE_COUNT) {
            logCount = 0;
        }
        
        logCount++;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        
        // 启动一个线程-每隔60s执行一遍
        new Timer().schedule(new TimerTask() {
            public void run() {
                refreshCacheData();
            }
        }, 10000, 60000);
    }
}
