package cn.jixunsoft.common.job;


/**
 * 一分钟定时器任务 Service 接口
 * 注：本接口用于定期加载缓存数据，执行时间比较长（超过一分钟）的job不要使用这个机制
 * 
 * @author zhuww
 * 
 * @date 2014-12-02 11:28:22
 */
public interface OneMinuteJobService {

    /**
     * 刷新缓存数据
     * 实现时new新的Map，加载完后一次性替换老的Map，防止线程安全问题
     * 
     * @return void
     */
    public void refreshCacheData();
}
