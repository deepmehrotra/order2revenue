package com.o2r.schedulers;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

public class SetTriggerJob implements
ApplicationListener<ContextStartedEvent>, ApplicationContextAware{
	
	@Override
	public void onApplicationEvent(ContextStartedEvent arg0) {
		System.out.println("Inside OnApplicationContext.. !!!");
	}
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		try{
			JobDetail job = JobBuilder.newJob(SampleJob.class) .withIdentity("setJob", "group1").build();
			
			Trigger trigger = TriggerBuilder.newTrigger() 
					.withIdentity("simpleTrigger", "group1") 
					.withSchedule(SimpleScheduleBuilder.simpleSchedule() 
					.withIntervalInSeconds(60).repeatForever()).build();
			
			Scheduler scheduler = new StdSchedulerFactory().getScheduler(); 
			scheduler.start(); 
			scheduler.scheduleJob(job, trigger);			
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}		
}
