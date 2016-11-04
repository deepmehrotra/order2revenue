package com.o2r.schedulers;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SetTriggerJobFlipkartSearch extends QuartzJobBean{
	
private FlipkartSearch flipkartSearch;
	
	static Logger log = Logger.getLogger(SetTriggerJobFlipkartSearch.class.getName());

	public void setFlipkartSearch(FlipkartSearch flipkartSearch) {
		
		this.flipkartSearch = flipkartSearch;
	}	
	@Override
	protected void executeInternal(JobExecutionContext arg0)throws JobExecutionException {		
		
		log.info("************************ Flipkart Schedular Executed At : "+ new Date());
			
			try {
				flipkartSearch.fixedDelayTask();
			} catch (JsonParseException e) {
				log.error("************************ Flipkart Schedular JsonParseException : "+ e);
			} catch (JsonMappingException e) {
				log.error("************************ Flipkart Schedular JsonMappingException : "+ e);
			} catch (IOException e) {
				log.error("************************ Flipkart Schedular IOException : "+ e);
			}
		
	}
}
