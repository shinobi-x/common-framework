package cn.jixunsoft.common.job;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.jixunsoft.common.spring.PropertiesUtils;

/**
 * 抽象并行定时器任务
 * 子类任务之间并行，一个任务执行过程中，其他任务也可以正常执行
 * 
 * @author zhuww
 * 
 * @date 2014年2月21日
 */
public abstract class AbstractParallelJob implements Job {

    private static final Logger logger = Logger.getLogger(AbstractParallelJob.class);

    private static final String TIMER_SWITCH_DIR = "timer";

    /*
     * Job名称
     */
    public abstract String getJobName();
    
    /*
     * Job开关文件，机器上存在该文件Job才执行
     */
    public String getSwitchFilePath() {
        String timerDir = PropertiesUtils.getString("timerDir", TIMER_SWITCH_DIR);
        return System.getProperty("user.home") + File.separator + timerDir + File.separator + getJobName() + ".switch";
    }

    @Override
    public void run() {

        String switchFilePath = getSwitchFilePath();
        if (StringUtils.isEmpty(switchFilePath) || !new File(switchFilePath).exists()) {
            logger.warn("Job mark file " + switchFilePath + " not found, cannot execute job " + getJobName());
            return;
        }
        
        try {
            logger.info(getJobName() + " begin execute ...");
            execute();
        } catch (Throwable t) {
            logger.error(getJobName() + " execute failed!", t);
        } finally {
            logger.info(getJobName() + " execute complete.");
        }
    }

    public abstract void execute();
    
}
