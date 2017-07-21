package com.google.code.shardbatis.plugin;

import java.sql.Connection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.google.code.shardbatis.builder.ShardConfigParser;
import com.google.code.shardbatis.builder.ShardStrategyHolder;
import com.google.code.shardbatis.strategy.IShardStrategy;
import com.google.code.shardbatis.util.ReflectionUtils;

/**
 * @author parcel
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class ShardPlugin implements Interceptor {
    private static final Log log = LogFactory.getLog(ShardPlugin.class);

    public static final String SHARDING_CONFIG = "shardingConfig";

    private static final ConcurrentHashMap<String, Boolean> cache = new ConcurrentHashMap<String, Boolean>();

    private static ShardStrategyHolder configFactory = ShardStrategyHolder.getInstance();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        MappedStatement mappedStatement = null;
        if (statementHandler instanceof RoutingStatementHandler) {
            StatementHandler delegate = (StatementHandler) ReflectionUtils.getFieldValue(statementHandler, "delegate");
            mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(delegate, "mappedStatement");
        } else {
            mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(statementHandler, "mappedStatement");
        }

        String mapperId = mappedStatement.getId();
        if (this.isParse(statementHandler, mappedStatement.getId())) {
            String sql = statementHandler.getBoundSql().getSql();
            if (log.isDebugEnabled()) {
                log.debug("Original Sql [" + mapperId + "]:" + sql);
            }

            // 根据MapperId获取mapperClass
            String mapperClass = mapperId.substring(0, mapperId.lastIndexOf("."));
            IShardStrategy shardStrategy = configFactory.getShardStrategy(mapperClass);
            if (shardStrategy != null) {

                // 直接将table强行替换
                Object params = statementHandler.getBoundSql().getParameterObject();

                // 处理空格换行等字符串问题
                String newTableName = String.format(" %s ", shardStrategy.getTargetTableName(params));
                String replaceTableName = String.format("(\\s|\t|\r|\n){1}%s(\\s|\t|\r|\n|\\;){1}", 
                        shardStrategy.getShardMapperConfig().getTableName());
                sql = sql.replaceAll(replaceTableName, newTableName);

                if (log.isDebugEnabled()) {
                    log.debug("Converted Sql [" + mapperId + "]:" + sql);
                }
                ReflectionUtils.setFieldValue(statementHandler.getBoundSql(), "sql", sql);
            }
        }

        return invocation.proceed();
    }

    /**
     * 是否解析
     * 
     * @param statementHandler
     * @param mapperId
     * @return
     */
    private boolean isParse(StatementHandler statementHandler, String mapperId) {
        Boolean parse = cache.get(mapperId);
        if (parse != null) {
            return parse;
        }

        // 初始化
        parse = false;

        // 根据MapperId获取mapperClass
        String mapperClass = mapperId.substring(0, mapperId.lastIndexOf("."));
        IShardStrategy shardStrategy = configFactory.getShardStrategy(mapperClass);

        // 1.<selectKey>不做解析 2.在ignoreList里的sql不用处理
        if (shardStrategy != null) {
            String method = mapperId.substring(mapperId.lastIndexOf(".") + 1);
            Set<String> ignoreList = shardStrategy.getShardMapperConfig().getIgnoreList();
            parse = !mapperId.endsWith("!selectKey") && !ignoreList.contains(method);
        }

        // 缓存mapperId是否解析结果
        cache.put(mapperId, parse);

        return parse;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

        // 解析配置文件
        String shardConfigFile = properties.getProperty(SHARDING_CONFIG, null);
        if (shardConfigFile == null || shardConfigFile.trim().length() == 0) {
            throw new IllegalArgumentException("property 'shardingConfig' is requested.");
        }

        try {
            ShardConfigParser.parse(shardConfigFile);
        } catch (Exception e) {
            throw new IllegalArgumentException("parse config file failed.");
        }
    }
    
    public static void main(String[] args){
        String newTableName = String.format(" %s ", "tb_game_1");
        String replaceTableName = String.format("(\\s|\t|\r|\n)%s(\\s|\t|\r|\n|\\;)?", "tb_game");
        String sql = "select *from tb_game\n left join a;";
        System.out.println(sql.replaceAll(replaceTableName, newTableName));
    }
    
}
