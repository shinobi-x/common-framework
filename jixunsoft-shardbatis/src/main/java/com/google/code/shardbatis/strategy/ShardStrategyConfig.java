package com.google.code.shardbatis.strategy;

import java.util.Map;

public class ShardStrategyConfig {
    /**
     * 分表策略
     */
    private String strategyClass;

    /**
     * 参数
     */
    private Map<String, String> params;

    public String getStrategyClass() {
        return strategyClass;
    }

    public void setStrategyClass(String strategyClass) {
        this.strategyClass = strategyClass;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

}
