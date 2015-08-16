package com.buseni.ubukwebwiza.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * This class is used to back up the database
 * A quartz job is used to call this class
 * @author habumugisha
 *
 */
//@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DatabaseBackupJob extends QuartzJobBean {
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(DatabaseBackupJob.class);

	//@Autowired
	private DatabaseBackup  databaseBackup;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		databaseBackup.exportDatabase();
		
	}
	public void setDatabaseBackup(DatabaseBackup databaseBackup) {
		this.databaseBackup = databaseBackup;
	}
	
	
	
}
