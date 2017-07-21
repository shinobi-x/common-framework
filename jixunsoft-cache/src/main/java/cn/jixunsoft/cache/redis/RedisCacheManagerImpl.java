package cn.jixunsoft.cache.redis;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import cn.jixunsoft.cache.utils.SerializeUtil;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Redis缓存
 * 
 * @author Danfo Yam
 * 
 *         2013年12月27日
 * 
 */
public class RedisCacheManagerImpl implements RedisCacheManager {

    private static final Logger logger = Logger.getLogger(RedisCacheManagerImpl.class);

    /**
     * redis shard池
     */
    private ShardedJedisPool pool;

    /**
     * @return the pool
     */
    @Override
    public ShardedJedisPool getPool() {
        return pool;
    }

    /**
     * @param pool
     *            the pool to set
     */
    public void setPool(ShardedJedisPool pool) {
        this.pool = pool;
    }

    /**
     * @return the jedis
     */
    @Override
    public ShardedJedis getJedis() {
        return pool.getResource();
    }

    @Override
    public void returnResource(ShardedJedis jedis) {
        try {
            if (jedis != null) {
                pool.returnResource(jedis);                
            }
        } catch (Exception e) {
            logger.error("return resource failed for:" + jedis, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object get(String key) {

        ShardedJedis jedis = pool.getResource();

        try {
            byte[] bkey = key.getBytes(Charset.forName("UTF-8"));

            byte[] bvalue = jedis.get(bkey);

            return SerializeUtil.deserialize(bvalue);

        } catch (Exception e) {
            logger.error("get failed by key:" + key, e);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean put(String key, Object value) {

        ShardedJedis jedis = pool.getResource();

        try {
            byte[] bkey = key.getBytes(Charset.forName("UTF-8"));
            Serializable serialObj = (Serializable) value;

            jedis.set(bkey, SerializeUtil.serialize(serialObj));

            return true;

        } catch (Exception e) {
            logger.error("put failed by key:" + key + ",value:" + value, e);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean put(String key, Object value, int timeout) {

        ShardedJedis jedis = pool.getResource();

        try {
            byte[] bkey = key.getBytes(Charset.forName("UTF-8"));
            Serializable serialObj = (Serializable) value;

            if (timeout > 0) {
                jedis.setex(bkey, timeout, SerializeUtil.serialize(serialObj));
                return true;
            }

            return put(key, value);

        } catch (Exception e) {
            logger.error("put failed by key:" + key + ",value:" + value + ",timeout:" + timeout, e);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean exists(String key) {

        ShardedJedis jedis = pool.getResource();

        try {
            return jedis.exists(key);

        } catch (Exception e) {
            logger.error("exist key failed!", e);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void expire(String key, int seconds) {

        ShardedJedis jedis = pool.getResource();

        try {
             jedis.expire(key, seconds);

        } catch (Exception e) {
            logger.error("expire key failed!", e);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);            
        }
    }

    @Override
    public void hmput(String key, Map map) {

        ShardedJedis jedis = pool.getResource();

        try {
            byte[] bkey = key.getBytes(Charset.forName("UTF-8"));
            Map<byte[], byte[]> bmap = new HashMap<byte[], byte[]>();

            // 将参数map转为二进制map
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry)iter.next();
                String field = (String) entry.getKey();
                Object mvalue = (Object) entry.getValue();
                byte[] bfield = field.getBytes(Charset.forName("UTF-8"));
                Serializable serialObj = (Serializable) mvalue;
                byte[] bmvalue = SerializeUtil.serialize(serialObj);
                bmap.put(bfield, bmvalue);
            }

            jedis.hmset(bkey, bmap);

        } catch (Exception e) {
            logger.error("hmput failed by key:" + key +",map:" + map, e);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void hmput(String key, String field, Object value) {

        ShardedJedis jedis = pool.getResource();

        try {
            byte[] bkey = key.getBytes(Charset.forName("UTF-8"));
            byte[] bfield = field.getBytes(Charset.forName("UTF-8"));
            Map<byte[], byte[]> bmap = new HashMap<byte[], byte[]>();

            Serializable serialObj = (Serializable) value;
            byte[] bvalue = SerializeUtil.serialize(serialObj);

            bmap.put(bfield, bvalue);

            jedis.hmset(bkey, bmap);

        } catch (Exception e) {
            logger.error("hmput failed by key:" + key +",field:" + field + ",value:" + value, e);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Object hget(String key, String field) {

        ShardedJedis jedis = pool.getResource();

        try {
            byte[] bkey = key.getBytes(Charset.forName("UTF-8"));
            byte[] bfield = field.getBytes(Charset.forName("UTF-8"));
            byte[] bvalue = jedis.hget(bkey, bfield);
            return SerializeUtil.deserialize(bvalue);

        } catch (Exception e) {
            logger.error("hget redis failed by key:" + key + ",field:" + field, e);
            throw new RuntimeException(e);

        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean delete(String key) {

        ShardedJedis jedis = pool.getResource();

        try {

//            byte[] bkey = key.getBytes(Charset.forName("UTF-8"));
            jedis.del(key);
            return true;

        } catch (Exception e) {
            logger.error("delete failed by key:" + key, e);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public boolean hdelete(String key, String field) {

        ShardedJedis jedis = pool.getResource();

        try {

            byte[] bkey = key.getBytes(Charset.forName("UTF-8"));
            byte[] bfield = field.getBytes(Charset.forName("UTF-8"));
            jedis.hdel(bkey, bfield);
            return true;

        } catch (Exception e) {
            logger.error("hdelete redis failed by key:" + key + ",field:" + field, e);
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public List<String> hmget(String key, List<String> fields) {

        // 如果fields空就直接返回了
        if (CollectionUtils.isEmpty(fields)) {
            return Collections.emptyList();
        }

        ShardedJedis jedis = pool.getResource();

        try {
            return jedis.hmget(key, fields.toArray(new String[0]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            returnResource(jedis);
        }
    }

}
