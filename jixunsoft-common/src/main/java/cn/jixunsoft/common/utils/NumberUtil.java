package cn.jixunsoft.common.utils;

import java.util.regex.Pattern;

/**
 * 数字类型工具类
 * 
 * @author Danfo Yam
 * 
 * @date 2013-7-29
 * 
 */
public class NumberUtil extends org.apache.commons.lang.math.NumberUtils {

    /**
     * 判断两个整形是否相等
     * 
     * @param i1
     *            integer one
     * @param i2
     *            integer two
     * 
     * @return true 为相等, false为不等
     */
    public static boolean equals(Integer i1, Integer i2) {
        return i1.compareTo(i2) == 0;
    }

    /**
     * 判断两个整形是否相等
     * 
     * @param i1
     *            integer one
     * @param i2
     *            integer two
     * 
     * @return true 为不等, false为相等
     */
    public static boolean notEquals(Integer i1, Integer i2) {
        return i1.compareTo(i2) != 0;
    }

    /**
     * 判断是否为整数
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
