package cn.jixunsoft.cache.redis;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * redis 缓存管理接口
 * 
 * @author Danfo Yam
 *
 * 2013年12月27日
 *
 */
public interface RedisCacheManager {

    /**
     * 存储key, value
     * 
     * @param key
     * @param value
     * 
     * @return 成功为true, 失败为false
     */
    public boolean put(String key, Object value);

    /**
     * 存储key, value, 并设置过期时间
     * 
     * @param key
     * @param value
     * @param timeout 过期时间, 单位为秒
     * 
     * @return 成功为true, 失败为false
     */
    public boolean put(String key, Object value, int timeout);

    /**
     * 将多个field - value(域-值)对设置到哈希表key中
     * 
     * @param key key
     * @param map 哈系表
     */
    public void hmput(String key, @SuppressWarnings("rawtypes") Map map);

    /**
     * 将field-value(域-值)对设置到哈系表的key中
     * 
     * @param key key
     * @param field 字段
     * @param value 值
     */
    public void hmput(String key, String field, Object value);

    /**
     * 删除key
     * 
     * @param key
     * 
     * @return 成功为true, 失败为false
     */
    public boolean delete(String key);

    /**
     * 删除key对应map中的field
     * 
     * @param key
     * @param field
     * 
     * @return 成功为true, 失败为false
     */
    public boolean hdelete(String key, String field);

    /**
     * 根据key获取
     * 
     * @param key
     * @return
     */
    public Object get(String key);

    /**
     * 从哈希表key中获取field的value
     * 
     * @param key redis key
     * @param field map中的字段
     * 
     * @return key对应字段的结果
     */
    public Object hget(String key, String field);

    /**
     * 判断key是否存在
     * 
     * @param key 
     * 
     * @return 存在为true，不存在为false
     */
    public boolean exists(String key);

    /**
     * 为给定key设置生命周期
     * 
     * @param key
     * @param seconds 生命周期 秒为单位
     */
    public void expire(String key, int seconds);

    /**
     * 将jedis放回池
     * 
     * @param jedis
     */
    public void returnResource(ShardedJedis jedis);

    /**
     * 从池中获取jedis
     * 
     * @return <code>ShardedJedis</code>
     */
    public ShardedJedis getJedis();

    /**
     * 获取池
     * 
     * @return <code>ShardedJedisPool</code>
     */
    public ShardedJedisPool getPool();

    /**
     * 从哈希表key中批量获取field的value
     * 
     * @return <code>mget</code>
     */
    public List<String> hmget(String key, List<String> fields);

}
