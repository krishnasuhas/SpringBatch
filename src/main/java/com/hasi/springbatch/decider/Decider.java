package com.hasi.springbatch.decider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import java.util.Random;

public class Decider {
    static final Logger logger = LoggerFactory.getLogger(Decider.class);

    public static JobExecutionDecider OddDecider = (JobExecution jobExecution, StepExecution stepExecution) -> {
        int randomInt = new Random().nextInt();
        String executionStatus;
        if (randomInt % 2 == 0) {
            executionStatus = ExecutionStatus.EVEN.toString();
        } else {
            executionStatus = ExecutionStatus.ODD.toString();
        }
        logger.info("got number {} and executing {}", randomInt, executionStatus);
        return new FlowExecutionStatus(executionStatus);
    };

}
