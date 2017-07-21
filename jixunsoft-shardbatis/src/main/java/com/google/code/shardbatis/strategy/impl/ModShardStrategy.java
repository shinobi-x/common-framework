package com.google.code.shardbatis.strategy.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.google.code.shardbatis.strategy.IShardStrategy;

/**
 * @author parcel
 * 
 */
public class ModShardStrategy extends IShardStrategy {

    @Override
    public String getTargetTableName(Object params) {
        Class<?> type = params.getClass();
        Boolean isPrimitive = type.isPrimitive();

        Map<String, String> propertyParams = shardMapperConfig.getShardStrategyConfig().getParams();
        Integer shardNum = Integer.valueOf(propertyParams.get("shardNum"));
        String shardPropertyName = propertyParams.get("shardPropertyName");

        /** 原始类型 */
        if (isPrimitive) {
            long identify = Long.parseLong(params.toString());
            return this.mod(shardMapperConfig.getTableName(), identify, shardNum);
        }

        /** 引用类型 */
        if (type == Long.class || type == Integer.class || type == Short.class) {
            return this.mod(shardMapperConfig.getTableName(), Long.parseLong(params.toString()), shardNum);
        }

        /** 正常类型 */
        try {
            String value = BeanUtils.getProperty(params, shardPropertyName);
            return this.mod(shardMapperConfig.getTableName(), Long.parseLong(value), shardNum);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String mod(String baseTableName, long identify, Integer shardNum) {
        long k = identify % shardNum;
        return baseTableName + "_" + k;
    }
}
