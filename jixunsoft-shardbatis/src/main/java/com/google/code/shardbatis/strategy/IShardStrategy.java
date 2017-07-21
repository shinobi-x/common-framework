package com.google.code.shardbatis.strategy;


public abstract class IShardStrategy {

    protected ShardMapperConfig shardMapperConfig;

    /**
     * 获取目标表名
     * 
     * @param params
     * @param shardMapperConfig
     * @return
     */
    public abstract String getTargetTableName(Object params);

    /**
     * @return
     */
    public ShardMapperConfig getShardMapperConfig() {
        return shardMapperConfig;
    }

    /**
     * @param shardMapperConfig
     */
    public void setShardMapperConfig(ShardMapperConfig shardMapperConfig) {
        this.shardMapperConfig = shardMapperConfig;
    }

}
