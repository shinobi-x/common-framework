package com.google.code.shardbatis.strategy.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.google.code.shardbatis.strategy.IShardStrategy;

/**
 * 按范围分表策略
 * 
 * @author lascala
 */
public class RangeShardStrategy extends IShardStrategy {

    @Override
    public String getTargetTableName(Object params) {
        Class<?> type = params.getClass();
        Boolean isPrimitive = type.isPrimitive();

        Map<String, String> propertyParams = shardMapperConfig.getShardStrategyConfig().getParams();
        String shardPropertyName = propertyParams.get("shardPropertyName");
        Integer shardRange = Integer.valueOf(propertyParams.get("shardRange"));
        String shardSplitCharacter = propertyParams.get("shardSplitCharacter");

        /** 原始类型 */
        if (isPrimitive) {
            long identify = Long.parseLong(params.toString());
            return this.range(shardMapperConfig.getTableName(), identify, shardRange, shardSplitCharacter);
        }

        /** 引用类型 */
        if (type == Long.class || type == Integer.class || type == Short.class) {
            return this.range(shardMapperConfig.getTableName(), Long.parseLong(params.toString()), shardRange, shardSplitCharacter);
        }

        /** 正常类型 */
        try {
            String value = BeanUtils.getProperty(params, shardPropertyName);
            return this.range(shardMapperConfig.getTableName(), Long.parseLong(value), shardRange, shardSplitCharacter);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String range(String baseTableName, long identify, Integer shardRange, String shardSplitCharacter) {
        long suffix = identify / shardRange;
        return baseTableName + shardSplitCharacter + suffix;
    }
}
