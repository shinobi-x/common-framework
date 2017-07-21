package cn.jixunsoft.common.spring;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class PropertiesUtils {

    private static Map<String, String> map = new HashMap<String, String>();

    /**
     * @param map the map to set
     */
    public static void setMap(Map<String, String> map) {
        PropertiesUtils.map = map;
    }

    /**
     * @return the map
     */
    public static Map<String, String> getMap() {
        return map;
    }

    public static String getString(String key, String defaultValue) {

        String value = (String) map.get(key);

        return value == null ? defaultValue : value;
    }

    public static Integer getInteger(String key, Integer defaultValue) {

        String value = (String) map.get(key);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return NumberUtils.toInt(value);
    }

    public static Long getLong(String key, Long defaultValue) {

        String value = (String) map.get(key);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return NumberUtils.toLong(value);
    }

    public static Double getDouble(String key, Double defaultValue) {

        String value = (String) map.get(key);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return NumberUtils.toDouble(value);
    }

    public static Float getFloat(String key, Float defaultValue) {

        String value = (String) map.get(key);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return NumberUtils.toFloat(value);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {

        String value = (String) map.get(key);

        return value == null ? defaultValue : BooleanUtils.toBoolean(value);
    }
}
