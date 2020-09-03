package com.hasi.springbatch.taskletsvschunks.config;

import com.hasi.springbatch.taskletsvschunks.chunks.LineProcessor;
import com.hasi.springbatch.taskletsvschunks.chunks.LineReader;
import com.hasi.springbatch.taskletsvschunks.chunks.LinesWriter;
import com.hasi.springbatch.taskletsvschunks.chunks.NameValidator;
import com.hasi.springbatch.taskletsvschunks.model.Line;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class ChunksConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public ItemReader<Line> itemReader() {
        return new LineReader();
    }

    @Bean
    public ItemWriter<Line> itemWriter() {
        return new LinesWriter();
    }

    public ValidatingItemProcessor<Line> validatingItemProcessor() {
        ValidatingItemProcessor<Line> validatingItemProcessor = new ValidatingItemProcessor<>(new NameValidator());
        validatingItemProcessor.setFilter(true);
        return validatingItemProcessor;
    }

    public ItemProcessor<Line, Line> itemProcessor() {
        return new LineProcessor();
    }

    @Bean
    public CompositeItemProcessor<Line, Line> compositeItemProcessor() throws Exception {
        List<ItemProcessor<Line, Line>> delegates = List.of(validatingItemProcessor(), itemProcessor());
        CompositeItemProcessor<Line, Line> compositeItemProcessor = new CompositeItemProcessor<>();
        compositeItemProcessor.setDelegates(delegates);
        compositeItemProcessor.afterPropertiesSet();
        return compositeItemProcessor;
    }

    @Bean
    protected Step processLines(ItemReader<Line> reader, ItemProcessor<Line, Line> processor, ItemWriter<Line> writer) {
        return steps.get("processLines").<Line, Line>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job() throws Exception {
        return jobs
                .get("chunksJob")
                .start(processLines(itemReader(), compositeItemProcessor(), itemWriter()))
                .build();
    }

}
