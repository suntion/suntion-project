package com.suns.quartz.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * TODO SampleStoreJob
 * @author suns suntion@yeah.net
 * @since 2017年12月21日下午3:39:37
 */
public class SampleStoreJob implements Job{
    private Logger logger = Logger.getLogger(SampleStoreJob.class);  
    
    @Override  
    public void execute(JobExecutionContext context) throws JobExecutionException {  
        logger.info("SampleStoreJob===========execute()");  
    }  
}
