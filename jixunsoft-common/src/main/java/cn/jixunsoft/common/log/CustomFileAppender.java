package cn.jixunsoft.common.log;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.log4j.FileAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 自定义日志文件Appender
 * 
 * @author zhuww
 * 
 * @date 2013年11月27日
 * 
 */
public class CustomFileAppender extends FileAppender {

    private static final String DATA_PATTERN = "%d\\{(.*?)}";
    private static final String TIMES_PATTERN = "%t\\{(.*?)}";

    private static final Pattern dataPattern = Pattern.compile(DATA_PATTERN);
    private static final Pattern timesPattern = Pattern.compile(TIMES_PATTERN);
    private static final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("javascript");

    private String customFile;

    static {
        try {
            // 获取主机名，保存为系统变量，log4j配置文件中可以使用系统变量
            System.setProperty("hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param customFile
     *            the customFile to set
     */
    public void setCustomFile(String customFile) {
        this.customFile = customFile;
    }

    @Override
    public void setFile(String file) {
        if (customFile == null) {
            return;
        }

        // 处理日期型配置
        file = customFile;
        Date current = new Date();
        Matcher matcher = dataPattern.matcher(file);
        while (matcher.find()) {
            file = file.replaceFirst(DATA_PATTERN, new SimpleDateFormat(matcher.group(1)).format(current));
        }

        // 处理表达式计算型配置
        matcher = timesPattern.matcher(file);
        while (matcher.find()) {
            try {
                file = file.replaceFirst(TIMES_PATTERN,
                        String.valueOf(((Double) scriptEngine.eval(matcher.group(1))).intValue() + 1));
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        super.setFile(file);
    }

    @Override
    public synchronized void doAppend(LoggingEvent event) {
        // 重命名后必须激活，新文件名才会生效
        this.setFile("");
        super.activateOptions();
        super.doAppend(event);
    }

}
