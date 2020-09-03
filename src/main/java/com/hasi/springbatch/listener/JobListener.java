package com.hasi.springbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.stereotype.Component;

@Component
public class JobListener {
    Logger logger = LoggerFactory.getLogger(JobListener.class);

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        logger.info(">> Before the Job {}", jobExecution.getJobInstance().getJobName());
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        logger.info("<< After the Job {}", jobExecution.getJobInstance().getJobName());
    }
}
