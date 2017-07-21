/**
 * 
 */
package com.google.code.shardbatis.builder;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author parcel
 * 
 * @since 2014-09-30 14:20
 * 
 */
public class ShardConfigParser {
    
    private static final Log log = LogFactory.getLog(ShardConfigParser.class);

    /**
     * 解析
     * 
     * @param input
     * @return
     * @throws Exception
     */
    public static void parse(String shardConfigFile) throws Exception {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(false);
        spf.setNamespaceAware(true);
        SAXParser parser = spf.newSAXParser();
        XMLReader reader = parser.getXMLReader();

        // 解析配置文件
        InputStream input = null;
        try {
            input = Resources.getResourceAsStream(shardConfigFile);
            DefaultHandler handler = new ReadXMLHandler();
            reader.setContentHandler(handler);
            reader.setEntityResolver(handler);
            reader.setErrorHandler(handler);
            reader.parse(new InputSource(input));
        } catch (IOException e) {
            log.error("Parse sharding config file failed.", e);
            throw new IllegalArgumentException(e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
