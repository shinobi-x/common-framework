package cn.jixunsoft.common.job;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.jixunsoft.common.spring.PropertiesUtils;

/**
 * 抽象定时器任务
 * 子类任务之间互斥，一个任务执行过程中，其他任务skip
 * 
 * @author Danfo Yam
 * 
 * @date 2013年9月10日
 */
public abstract class AbstractJob implements Job {

    private static final Logger logger = Logger.getLogger(AbstractJob.class);

    private static final Lock lock = new ReentrantLock();
    
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
        
        if (lock.tryLock()) {

            try {
                logger.info(getJobName() + " begin execute ...");
                execute();
            } catch (Throwable t) {
                logger.error(getJobName() + " execute failed!", t);
            } finally {
                lock.unlock();
                logger.info(getJobName() + " execute complete.");
            }
        } else {
            logger.info(getJobName() + " skiped.");
        }
    }

    public abstract void execute();
}
