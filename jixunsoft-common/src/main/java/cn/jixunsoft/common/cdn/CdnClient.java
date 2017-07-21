package cn.jixunsoft.common.cdn;


public interface CdnClient {

    /**
     * 刷新节点上的内容
     * 
     * @return RefreshTaskId
     * @throws Throwable
     */
    public String refreshCaches(String objectPath) throws Throwable;

    /**
     * 将内容预热到Cache节点，用户首次访问可直接命中缓存，缓解源站压力
     * 
     * @return PushTaskId
     * @throws Throwable
     */
    public String pushCache(String objectPath) throws Throwable;
    
}
