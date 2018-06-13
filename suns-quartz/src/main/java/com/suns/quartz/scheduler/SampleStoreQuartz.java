package com.suns.quartz.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.suns.quartz.job.SampleStoreJob;

/**
 * TODO SampleStoreQuartz
 * @author suns suntion@yeah.net
 * @since 2017年12月21日下午3:41:45
 */
public class SampleStoreQuartz {
    public void run() throws Exception{  
        //使用SchedulerFactory创建一个Scheduler  
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();  
        Scheduler scheduler = schedulerFactory.getScheduler();  
        scheduler.clear(); //测试用，避免因为调度存在报错，可以在job未delete的情况下删掉看下效果  
  
        //定义一个具体的Job  
        JobDetail jobDetail = JobBuilder.newJob(SampleStoreJob.class).withIdentity("sampleStoreJob", "sampleJobGroup").build();  
        //定义一个具体的Trigger  
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");//具体的执行时间定义  
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("sampleStoreTrigger", "sampleTriggerGroup").withSchedule(scheduleBuilder).build();  
        //将Job和Trigger绑定至Scheduler  
        scheduler.scheduleJob(jobDetail, trigger);  
        scheduler.start();//启动运行  
          
        Thread.sleep(100*1000);//情节需要，10秒钟  
        //定义一个JobKey，用来做删除Job测试  
        JobKey jobKey = JobKey.jobKey("sampleStoreJob", "sampleJobGroup");  
        scheduler.deleteJob(jobKey);  
          
        scheduler.shutdown();//关闭Scheduler  
    }  
      
    public static void main(String[] args) throws Exception{  
        SampleStoreQuartz sampleStoreQuartz = new SampleStoreQuartz();  
        sampleStoreQuartz.run();  
    }  
}
