package com.hasi.springbatch.configuration;

import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FlowConfiguration {

    @Autowired
    private StepConfiguration stepConfiguration;

    @Bean
    public Flow flow1() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow1 - series of steps");
        flowBuilder.start(stepConfiguration.step1())
                .next(stepConfiguration.step2())
                .next(stepConfiguration.step4())
                .end();
        return flowBuilder.build();
    }
}
