package com.hasi.springbatch.configuration;

import com.hasi.springbatch.decider.Decider;
import com.hasi.springbatch.decider.ExecutionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(JobConfiguration.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepConfiguration stepConfiguration;

    @Autowired
    private FlowConfiguration flowConfiguration;

    @Bean(name = "job0")
    public Job job0() {
        return jobBuilderFactory.get("Job0 with one step")
                .start(stepConfiguration.step1())
                .build();
    }

    @Bean
    public Job job1() {
        return jobBuilderFactory.get("Job1 multiple steps based on conditions and may fail")
                .start(stepConfiguration.step1())
                .on("COMPLETED").to(stepConfiguration.step2())
                .from(stepConfiguration.step2())
                .next(stepConfiguration.step3()) // "next(step3())" is same as on("COMPLETED").to(step3()).from(step3())
                .end()
                .build();
    }

    @Bean
    public Job job2() {
        return jobBuilderFactory.get("Job2 with flow first")
                .start(flowConfiguration.flow1())
                .next(stepConfiguration.step3()) // you can use "next" when transitioning from flow to step
                .end()
                .build();
    }

    @Bean
    public Job job3() {
        return jobBuilderFactory.get("Job3 with flow last")
                .start(stepConfiguration.step3())
                .on("COMPLETED").to(flowConfiguration.flow1()) // you cant simply use "next" when going from step to flow
                .end()
                .build();
    }

    @Bean
    public Job job4() {
        return jobBuilderFactory.get("Job4 with split for parallel execution")
                .start(stepConfiguration.step5())
                .split(new SimpleAsyncTaskExecutor()).add(flowConfiguration.flow1())// to execute in parallel with step3
                .next(stepConfiguration.step3())
                .end()
                .build();
    }

    @Bean
    public Job job5() {
        return jobBuilderFactory.get("job5 with decider acts as while loop runs until got even")
                .start(stepConfiguration.step1())
                .next(Decider.OddDecider)
                .from(Decider.OddDecider).on(ExecutionStatus.EVEN.toString()).to(stepConfiguration.stepEven())
                .from(Decider.OddDecider).on(ExecutionStatus.ODD.toString()).to(stepConfiguration.stepOdd())
                .from(stepConfiguration.stepOdd()).on(ExecutionStatus.ANY.toString()).to(Decider.OddDecider)
                .end()
                .build();
    }

    @Bean
    public Job job6() {
        return jobBuilderFactory.get("job6 this is a child job")
                .start(stepConfiguration.step2())
                .build();
    }

    @Bean
    public Job job7(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return jobBuilderFactory.get("job7 this is a parent job")
                .start(stepConfiguration.step1())
                .next(stepConfiguration.stepChild(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Job job8() {
        return jobBuilderFactory.get("job8 process the chunks")
                .start(stepConfiguration.stepChunk())
                .build();
    }

    @Bean
    public Job job9() {
        return jobBuilderFactory.get("job9 parameters for job Instance")
                .start(stepConfiguration.stepParam())
                .build();
    }

    @Bean
    public Job job10() {
        return jobBuilderFactory.get("job10 readerJob")
                .start(stepConfiguration.stepStatelessReader())
                .build();
    }

    @Bean
    public Job job11() throws Exception {
        return jobBuilderFactory.get("job11 sqlJob")
                .start(stepConfiguration.stepSqlReader())
                .build();
    }

    @Bean
    public Job job12() throws Exception {
        return jobBuilderFactory.get("job12 stateFull streamReader")
                .start(stepConfiguration.stepStateFullStreamReader())
                .build();
    }

    /**
     * Number of records are 100 and
     * number of steps are 2 and
     * chunk size is 10
     * if the batch fails at 56 then restart will start at
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Job job13() throws Exception {
        return jobBuilderFactory.get("job13 retry")
                .start(stepConfiguration.stepRetry())
                .next(stepConfiguration.stepRetry2())
                .build();
    }
}
