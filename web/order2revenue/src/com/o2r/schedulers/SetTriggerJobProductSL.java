package com.o2r.schedulers;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SetTriggerJobProductSL extends QuartzJobBean{
	
private SampleJob sampleJob;
	
	public void setSampleJob(SampleJob sampleJob) {
		this.sampleJob = sampleJob;
	}	
	@Override
	protected void executeInternal(JobExecutionContext arg0)throws JobExecutionException {		
		//sampleJob.executeJobProductStockList();
	}
}
