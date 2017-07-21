package com.google.code.shardbatis.strategy.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import com.google.code.shardbatis.strategy.IShardStrategy;

/**
 * 根据年月为分表策略
 * 
 * @author danfo
 *
 */
public class MonthShardStrategy extends IShardStrategy {

    /**
     * 年月格式
     */
    private static final String MONTH_FORMAT = "yyyy_MM";

    /**
     * 年月日格式
     */
    private static final String[] DATE_FORMAT = {"yyyy-MM-dd"};

    @Override
    public String getTargetTableName(Object params) {

        Class<?> type = params.getClass();
        Boolean isPrimitive = type.isPrimitive();

        Map<String, String> propertyParams = shardMapperConfig.getShardStrategyConfig().getParams();
        String shardPropertyName = propertyParams.get("shardPropertyName");
        String shardSplitCharacter = propertyParams.get("shardSplitCharacter");

        /** 原始类型 */
        if (isPrimitive) {
            throw new RuntimeException("Not exist split table info:" + params);
        }

        /** pojo类型 */
        try {

            String value = BeanUtils.getProperty(params, shardPropertyName);

            if (StringUtils.isEmpty(value)) {
                throw new RuntimeException("Not exist split table info:" + params);
            }

            Date date = DateUtils.parseDate(value, DATE_FORMAT);

            String month = DateFormatUtils.format(date, MONTH_FORMAT);

            return shardMapperConfig.getTableName() + shardSplitCharacter + month;

        } catch (Exception e) {
            throw new RuntimeException("Not exist split table info:" + params, e);
        }
    }
}
