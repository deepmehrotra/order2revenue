package com.schedulers;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.schedulers.HelloJob;

public class CStartEventHandler implements
		ApplicationListener<ContextStartedEvent>, ApplicationContextAware {

	ApplicationContext arg0 = null;
	static int a = 0;

	public void onApplicationEvent(ContextStartedEvent event) {
		System.out.println("ContextStartedEvent Received");

	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stubS
		this.arg0 = arg0;
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			// 0 0 1 * *
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("dummyTriggerName", "group1")
					.withSchedule(
							CronScheduleBuilder.cronSchedule("0/60 * * * * ?"))
					.build();

			JobDetail job = JobBuilder.newJob(HelloJob.class)
					.withIdentity("job1", "group1").build();
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
			Thread.sleep(50000000);
		} catch (Exception se) {
			se.printStackTrace();
		}
	}
}