package com.inventory;
import java.text.ParseException;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;


import org.quartz.JobDetail;

public class QuartzTest {

    public static void main(String[] args) throws InterruptedException, ParseException {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            
            Trigger trigger = TriggerBuilder
            		.newTrigger()
            		.withIdentity("dummyTriggerName", "group1")
            		.withSchedule(
            			CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
            		.build();
            
            
            
            
            JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();
            scheduler.scheduleJob(job, trigger);
            System.out.println("THIS IS THE CRON JOB START");
            scheduler.resumeAll();
         //   scheduler.start();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}