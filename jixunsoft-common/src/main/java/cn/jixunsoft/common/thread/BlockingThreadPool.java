package cn.jixunsoft.common.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

/** 
* 阻塞的线程池
*
* @author zhuww
*
* @date 2014年2月18日
*  
*/
public class BlockingThreadPool {

    private static final Logger logger = Logger.getLogger(BlockingThreadPool.class);
    
    private final ExecutorService pool;
    
    private final BlockingQueue<Runnable> blockingQueue;
    
    /**
     * 初始化线程池
     * @param size 线程池大小
     */
    public BlockingThreadPool(int size){
        pool = Executors.newFixedThreadPool(size);
        blockingQueue = new LinkedBlockingQueue<Runnable>(size);
    }
    
    /**
     * 提交任务
     * @param thread
     * @return
     * @throws InterruptedException
     */
    public Future<?> submit(Runnable thread) throws InterruptedException{
        
        blockingQueue.put(thread);
        
        return pool.submit(new Runnable() {
            
            @Override
            public void run() {
                try {
                    Runnable task = null;
                    synchronized (blockingQueue) {
                        task = blockingQueue.take();
                    }
                    
                    if(task == null){
                        return;
                    }
                    
                    task.run();
                } catch (InterruptedException e) {
                    logger.error(e);
                }
            }
        });
    }
}
