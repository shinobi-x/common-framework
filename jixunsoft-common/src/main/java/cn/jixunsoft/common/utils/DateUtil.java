package cn.jixunsoft.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * UC日期工具类
 * 
 * @author Danfo Yam
 * 
 * @date 2014-08-12
 *
 */
public class DateUtil {

    /**
     * 根据指定日期字符串和偏移量，获取指定日期字符串+偏移量的日期
     * 
     * <p>
     * 例如当前为2014-08-11,偏移量为-1，则返回2014-08-10字符串
     * </p>
     * 
     * @param time 指定日期字符串
     * @param pattern 指定日期格式
     * @param offset 偏移量
     * @param offsetPattern 偏移后的日期格式
     * 
     * @return 偏移日期字符串
     */
    public static String getOffsetTime(String time, String pattern, int offset, String offsetPattern) {

        try {
            Date date = DateUtils.parseDate(time, new String[] {pattern});
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, offset);

            DateFormat format = new SimpleDateFormat(offsetPattern);

            String curDate = format.format(calendar.getTime());

            return curDate;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据指定日期和偏移量，获取指定日期+偏移量的日期字符串
     * 
     * <p>
     * 例如当前为2014-08-11,偏移量为-1，则返回2014-08-10
     * </p>
     * 
     * @param date 指定日期
     * @param offset 偏移量
     * @param pattern 日期格式
     * 
     * @return 偏移日期字符串
     */
    public static String getOffsetTime(Date date, int offset, String pattern) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset);

        DateFormat format = new SimpleDateFormat(pattern);

        String curDate = format.format(calendar.getTime());

        return curDate;
    }

    /**
     * 根据指定日期和偏移量, 获取指定日期+偏移量的日期
     * 
     * @param date 指定日期
     * @param offset 偏移量
     * 
     * @return 偏移日期
     */
    public static Date getOffsetTime(Date date, int offset) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset);

        return calendar.getTime();
    }

    /**
     * 根据指定日期字符串和偏移量, 获取指定日期+偏移量的日期
     * 
     * @param dateStr 指定日期字符串
     * @param pattern 指定日期字符串格式
     * @param offset 偏移量
     * 
     * @return 偏移日期
     */
    public static Date getOffsetTime(String dateStr, String pattern, int offset) {

        try {
            Date date = DateUtils.parseDate(dateStr, new String[] {pattern});
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, offset);

            return calendar.getTime();

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据日期字符串和格式，返回日期
     * 
     * @param dateStr 日期字符串
     * @param pattern 格式
     * 
     * @return object of <code>java.util.Date</code>
     */
    public static Date getDate(String dateStr, String pattern) {

        try {
            return DateUtils.parseDate(dateStr, new String[] {pattern});
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Long getTodayZero() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();

    }
    
    /**
     * 根据指定日期和偏移量, 获取指定日期+偏移量的日期
     * 
     * @param date 指定日期
     * @param offset 偏移量
     * @param unit 单位
     * 
     * @return 偏移日期
     */
    public static Date getOffsetTime(Date date, int offset, int unit) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit, offset);

        return calendar.getTime();
    }
    
    public static long getZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

}
