package com.o2r.schedulers;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.o2r.controller.AdminController;


public class SetTriggerJob extends QuartzJobBean{

	private SampleJob sampleJob;
	static Logger log = Logger.getLogger(AdminController.class.getName());
	
	public void setSampleJob(SampleJob sampleJob) {
		this.sampleJob = sampleJob;
	}	
	@Override
	protected void executeInternal(JobExecutionContext arg0)throws JobExecutionException {	
		log.info("$$$ executeInternal() Starts : SetTriggerJob $$$ ");
		sampleJob.executeJobPaymentDueDate();
		log.info("$$$ executeInternal() Exits : SetTriggerJob $$$ ");
	}		
}
