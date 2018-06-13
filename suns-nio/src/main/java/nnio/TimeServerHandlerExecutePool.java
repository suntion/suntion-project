/**
 * 
 */
package nnio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO TimeServerHandlerExecutePool
 * @author suns sontion@yeah.net
 * @since 2017年5月9日下午2:59:00
 */
public class TimeServerHandlerExecutePool {
    
    private ExecutorService executor;
    
    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }
    
    
    public void execute(Runnable task){
        executor.execute(task);
    }
}