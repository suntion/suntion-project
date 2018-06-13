package com.suns.quartz.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * TODO HelloQuartz
 * @author suns suntion@yeah.net
 * @since 2017年12月21日下午4:29:53
 */
public class HelloQuartz implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail detail = context.getJobDetail();
        String name = detail.getJobDataMap().getString("name");
        System.out.println("say hello to " + name + " at " + new Date());
    }

}
