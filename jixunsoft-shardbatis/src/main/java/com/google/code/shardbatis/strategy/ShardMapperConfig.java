package com.google.code.shardbatis.strategy;

import java.util.Set;

public class ShardMapperConfig {
    /**
     * mapper class
     */
    private String mapperClass;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 分表策略
     */
    private ShardStrategyConfig shardStrategyConfig;

    /**
     * 忽略接口列表
     */
    private Set<String> ignoreList;

    public String getMapperClass() {
        return mapperClass;
    }

    public void setMapperClass(String mapperClass) {
        this.mapperClass = mapperClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ShardStrategyConfig getShardStrategyConfig() {
        return shardStrategyConfig;
    }

    public void setShardStrategyConfig(ShardStrategyConfig shardStrategyConfig) {
        this.shardStrategyConfig = shardStrategyConfig;
    }

    public Set<String> getIgnoreList() {
        return ignoreList;
    }

    public void setIgnoreList(Set<String> ignoreList) {
        this.ignoreList = ignoreList;
    }
}
