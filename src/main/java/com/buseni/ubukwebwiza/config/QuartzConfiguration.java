package com.buseni.ubukwebwiza.config;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.buseni.ubukwebwiza.jobs.DatabaseBackup;
import com.buseni.ubukwebwiza.jobs.DatabaseBackupJob;
import com.buseni.ubukwebwiza.jobs.MyJobTwo;
//@Configuration 
//@ComponentScan("com.buseni.ubukwebwiza") 
public class QuartzConfiguration {
	
	@Autowired
	Environment environment;
	
	@Autowired
	private DatabaseBackup  databaseBackup;
	@Bean
	public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
		MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
		obj.setTargetBeanName("databaseBackup");
		obj.setTargetMethod("exportDatabase");
		return obj;
	}
	
	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean(){
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(DatabaseBackupJob.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("databaseBackup", databaseBackup);
	  factory.setJobDataAsMap(map);
	//	factory.setGroup("mygroup");
	//	factory.setName("myjob");
		return factory;
	} 
	
	//Job is scheduled after every 1 minute 
	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean(){
		CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
		stFactory.setJobDetail(jobDetailFactoryBean().getObject());
		//stFactory.setStartDelay(3000);
		stFactory.setCronExpression("0 0/2 * 1/1 * ? *");
		return stFactory;
	}
	
	
	
	/*@Bean(name="databaseBackup")
	public DatabaseBackup databaseBackup(){
		
		DatabaseBackup databaseBackup = new DatabaseBackup();
		databaseBackup.setBucketName(enviaws.accessKeyronment.getProperty("aws.backupBucketName"));
		databaseBackup.setAccessKey(environment.getProperty("aws.accessKey"));
		databaseBackup.setSecretKey(environment.getProperty("aws.secretKey"));
		databaseBackup.setHost(environment.getProperty("datasource.host"));
		databaseBackup.setPort(environment.getProperty("datasource.port"));
		databaseBackup.setDatabaseName(environment.getProperty("datasource.dbname"));
		databaseBackup.setUsername(environment.getProperty("datasource.username"));
		databaseBackup.setPassword(environment.getProperty("datasource.password"));
		return databaseBackup;
		
	}*/
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		//scheduler.setJobDetails(methodInvokingJobDetailFactoryBean().getObject());
		scheduler.setTriggers(cronTriggerFactoryBean().getObject());
		//scheduler.setWaitForJobsToCompleteOnShutdown(true);
		return scheduler;
	}
} 

