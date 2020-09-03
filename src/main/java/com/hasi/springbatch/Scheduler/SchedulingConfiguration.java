package com.hasi.springbatch.Scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
public class SchedulingConfiguration {
    Logger logger = LoggerFactory.getLogger(SchedulingConfiguration.class);

    @Autowired
    JobLauncher jobLauncher;

    @Resource(name = "job0")
    Job job;


    @Scheduled(cron = "*/5 * 1 * * MON-FRI")
    void sampleJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        logger.info("Current time in sampleJob is {}", new Date());
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(new HashMap<>()));
        while (jobExecution.isRunning()) {
            logger.info("Batch is running");
        }
        logger.info("Job Execution Status : " + jobExecution.getStatus());
    }

    //    @Scheduled(initialDelay = 1000L, fixedRate = 5000L)
    //    @Scheduled(fixedDelayString = "PT1S")
    //    @Scheduled(fixedDelay = 1000L)
    void sampleJob2() throws InterruptedException {
        logger.info("Current time is in sampleJob2 {}", new Date());
        Thread.sleep(1000L);
    }
}
