package com.hasi.springbatch.configuration;

import com.hasi.springbatch.domain.Billionaire;
import com.hasi.springbatch.listener.ChunkListener;
import com.hasi.springbatch.listener.JobListener;
import com.hasi.springbatch.processor.DummyProcessor;
import com.hasi.springbatch.readers.ListReader;
import com.hasi.springbatch.readers.SqlReader;
import com.hasi.springbatch.readers.StateFullStreamReader;
import com.hasi.springbatch.readers.StatelessItemReader;
import com.hasi.springbatch.writer.logWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Component
public class StepConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(StepConfiguration.class);

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobConfiguration jobConfiguration;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ListReader listReader;

    @Autowired
    private logWriter logWriter;

    @Autowired
    private SqlReader sqlReader;

    @Autowired
    private TaskletConfiguration taskletConfiguration;

    @Autowired
    private DummyProcessor dummyProcessor;

    @Bean
    public Step stepCounting() {
        return stepBuilderFactory.get("step3")
                .tasklet(TaskletConfiguration.countingTasklet)
                .build();
    }

    @Bean
    public Step stepOdd() {
        return stepBuilderFactory.get("stepOdd")
                .tasklet((stepContribution, chunkContext) -> {
                    logger.info("stepOdd");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step stepEven() {
        return stepBuilderFactory.get("stepEven")
                .tasklet((stepContribution, chunkContext) -> {
                    logger.info("stepEven");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step stepChunk() {
        return stepBuilderFactory.get("stepChunk")
                .<String, String>chunk(2)
                .faultTolerant()// to enable listeners
                .listener(new JobListener())
                .listener(new ChunkListener())
                .reader(listReader.inMemoryReader())
                .writer(logWriter.consoleWriter())
                .build();
    }

    @Bean
    public Step stepParam() {
        return stepBuilderFactory.get("stepParam")
                .tasklet(taskletConfiguration.jobParameterTasklet(null))
                .build();
    }

    @Bean
    public Step stepStatelessReader() {
        return stepBuilderFactory.get("stepStatelessReader")
                .<String, String>chunk(2)
                .reader(new StatelessItemReader(List.of("A", "B", "C")))
                .writer(logWriter.consoleWriter())
                .build();

    }

    @Bean
    public Step stepSqlReader() throws Exception {
        return stepBuilderFactory.get("stepSqlReader")
                .<Billionaire, Billionaire>chunk(2)
                .reader(sqlReader.cursorItemReader())
                .writer(logWriter.consoleSqlWriter())
                .build();

    }

    @Bean
    public Step stepStateFullStreamReader() {
        return stepBuilderFactory.get("stepStateFullStreamReader")
                .<String, String>chunk(2)
                .reader(new StateFullStreamReader(List.of("1", "2", "3", "4", "5")))
                .writer(logWriter.consoleWriter())
                .build();
    }

    @Bean
    public Step stepRetry() {
        return stepBuilderFactory.get("stepStatelessReader")
                .<String, String>chunk(2)
                .reader(new ListItemReader<>(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")))
                .processor(dummyProcessor)
                .writer(logWriter.consoleWriter())
                .build();
    }

    @Bean
    public Step stepRetry2() {
        return stepBuilderFactory.get("stepStatelessReader")
                .<String, String>chunk(2)
                .reader(new ListItemReader<>(List.of("11", "22", "33", "44", "55", "66", "77", "88", "99", "100")))
                .processor(dummyProcessor)
                .writer(logWriter.consoleWriter())
                .build();
    }

    @Bean
    public Step stepChild(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(stepBuilderFactory.get("childJobStep"))
                .job(jobConfiguration.job6())
                .launcher(jobLauncher)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    logger.info("step1");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((stepContribution, chunkContext) -> {
                    logger.info("step2");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((stepContribution, chunkContext) -> {
                    logger.info("step3");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step4() {
        return stepBuilderFactory.get("step4")
                .tasklet((stepContribution, chunkContext) -> {
                    logger.info("step4");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step step5() {
        return stepBuilderFactory.get("step5")
                .tasklet((stepContribution, chunkContext) -> {
                    logger.info("step5");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
