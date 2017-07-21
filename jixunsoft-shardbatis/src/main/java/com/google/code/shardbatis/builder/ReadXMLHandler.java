package com.google.code.shardbatis.builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.code.shardbatis.plugin.ShardPlugin;
import com.google.code.shardbatis.strategy.IShardStrategy;
import com.google.code.shardbatis.strategy.ShardMapperConfig;
import com.google.code.shardbatis.strategy.ShardStrategyConfig;

public class ReadXMLHandler extends DefaultHandler {

    private static final Log log = LogFactory.getLog(ShardPlugin.class);

    private static final String SHARD_CONFIG_DTD = "shardbatis-config.dtd";

    private static final Map<String, String> DOC_TYPE_MAP = new HashMap<String, String>();

    private static ShardStrategyHolder shardStrategyHolder = ShardStrategyHolder.getInstance();

    private ShardMapperConfig shardMapperConfig;

    private ShardStrategyConfig shardStrategyConfig;

    private String parentElement;

    static {
        DOC_TYPE_MAP.put("http://shardbatis.googlecode.com/dtd/shardbatis-config.dtd".toLowerCase(), SHARD_CONFIG_DTD);
        DOC_TYPE_MAP.put("-//shardbatis.googlecode.com//DTD Shardbatis 2.0//EN".toLowerCase(), SHARD_CONFIG_DTD);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("shardMapper".equals(qName)) {
            shardMapperConfig = new ShardMapperConfig();
            String mapperClass = attributes.getValue("mapperClass");
            shardMapperConfig.setMapperClass(mapperClass);
            shardMapperConfig.setTableName(attributes.getValue("tableName"));
            shardMapperConfig.setIgnoreList(new HashSet<String>());
        } else if ("shardStrategy".equals(qName)) {
            parentElement = "shardStrategy";
            shardStrategyConfig = new ShardStrategyConfig();
            shardStrategyConfig.setParams(new HashMap<String, String>());
            shardStrategyConfig.setStrategyClass(attributes.getValue("strategyClass"));

            shardMapperConfig.setShardStrategyConfig(shardStrategyConfig);
        } else if ("shardStrategy".equals(parentElement) && "property".equals(qName)) {
            String key = attributes.getValue("name");
            String value = attributes.getValue("value");

            shardStrategyConfig.getParams().put(key, value);
        } else if ("ignoreList".equals(qName)) {
            parentElement = "ignoreList";
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("shardStrategy".equals(qName)) {
            // 实例化分表策略实现
            IShardStrategy shardStrategy = null;
            try {
                Class<?> clazz = Class.forName(shardStrategyConfig.getStrategyClass());
                shardStrategy = (IShardStrategy) clazz.newInstance();
            } catch (Exception e) {
                log.error("实例化分表策略类失败", e);
                throw new SAXException(e);
            }

            shardStrategy.setShardMapperConfig(shardMapperConfig);
            shardStrategyHolder.register(shardMapperConfig.getMapperClass(), shardStrategy);
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        if ("ignoreList".equals(parentElement)) {
            shardMapperConfig.getIgnoreList().add(new String(ch, start, length).trim());
        }
    }

    public void error(SAXParseException e) throws SAXException {
        throw e;
    }

    public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
        if (publicId != null)
            publicId = publicId.toLowerCase();
        if (systemId != null)
            systemId = systemId.toLowerCase();

        InputSource source = null;
        try {
            String path = DOC_TYPE_MAP.get(publicId);
            source = getInputSource(path, source);
            if (source == null) {
                path = DOC_TYPE_MAP.get(systemId);
                source = getInputSource(path, source);
            }
        } catch (Exception e) {
            throw new SAXException(e.toString());
        }
        return source;
    }

    private InputSource getInputSource(String path, InputSource source) {
        if (path != null) {
            InputStream in = null;
            try {
                in = Resources.getResourceAsStream(path);
                source = new InputSource(in);
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        }
        return source;
    }
}
