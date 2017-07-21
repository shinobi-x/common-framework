/**
 * 
 */
package com.google.code.shardbatis.builder;

import java.util.HashMap;
import java.util.Map;

import com.google.code.shardbatis.strategy.IShardStrategy;

/**
 * @author parcel
 * 
 * @since 2014-09-30 14:20
 * 
 */
public class ShardStrategyHolder {

    private static final ShardStrategyHolder instance = new ShardStrategyHolder();

    private Map<String, IShardStrategy> shardStrategyRegister = new HashMap<String, IShardStrategy>();

    private ShardStrategyHolder() {
    }

    public static ShardStrategyHolder getInstance() {
        return instance;
    }

    /**
     * 注册分表策略
     * 
     * @param mapperClass
     * @param shardMapper
     */
    public void register(String mapperClass, IShardStrategy shardStrategy) {
        this.shardStrategyRegister.put(mapperClass.toLowerCase(), shardStrategy);
    }

    /**
     * 查找对应表的分表策略
     * 
     * @param mapperClass
     * @return
     */
    public IShardStrategy getShardStrategy(String mapperClass) {
        return shardStrategyRegister.get(mapperClass.toLowerCase());
    }
}
