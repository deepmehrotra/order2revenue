package com.tutorialspoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.inventory.HelloJob;

public class CStartEventHandler 
   implements ApplicationListener<ContextStartedEvent>, ApplicationContextAware{
	
	ApplicationContext arg0=null;
	static int a=0;

   public void onApplicationEvent(ContextStartedEvent event) {
      System.out.println("ContextStartedEvent Received");
      
      
      
      
      
      System.out.println("MAHESH KUMAR KOLLIPARA@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
      
      try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
	/*	
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
           scheduler.start();
           
       } catch (SchedulerException se) {
           se.printStackTrace();
       }*/
   }

@Override
public void setApplicationContext(ApplicationContext arg0)
		throws BeansException {
	// TODO Auto-generated method stubS
	this.arg0=arg0;
				
	
	
	try{
		FileReader reader=new FileReader("testfile.txt");
	    BufferedReader br=new BufferedReader(reader);
	    br.readLine();
	    System.out.println("THIS IS APPLICATION CONTEXT AWARE TEST BEAN");
	    br.close();
	    File file=new File("testfile.txt");
	    file.delete();
	}catch(Exception ee){
		FileWriter out;
		try {
			out = new FileWriter("testfile.txt");
			out.write("TESTFILE");	
			out.close();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
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
        scheduler.start();
        System.out.println("MAHESH KUMAR KOLLIPARA");
        Thread.sleep(5000);
    } catch (Exception se) {
        se.printStackTrace();
    } 
}
}