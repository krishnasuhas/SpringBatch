package com.hasi.springbatch.writer;

import com.hasi.springbatch.domain.Billionaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class logWriter {
    Logger logger = LoggerFactory.getLogger(logWriter.class);

    @Bean
    public ItemWriter<String> consoleWriter() {
        return list -> list.forEach(s -> logger.info("written {}", s));
    }

    @Bean
    public ItemWriter<Billionaire> consoleSqlWriter() {
        return list -> list.forEach(s -> logger.info("written {}", s));
    }
}
