package com.hasi.springbatch.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TaskletConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(TaskletConfiguration.class);

    @StepScope
    public static final Tasklet countingTasklet = (StepContribution stepContribution, ChunkContext chunkContext) -> {
        logger.info("{} has been executed on thread {}", chunkContext.getStepContext(), Thread.currentThread().getName());
        return RepeatStatus.FINISHED;
    };

    @Bean
    @StepScope
    public Tasklet jobParameterTasklet(@Value("#{jobParameters['message']}") String message) {
        return (StepContribution stepContribution, ChunkContext chunkContext) -> {
            logger.info(message);
            return RepeatStatus.FINISHED;
        };
    }
}
