package com.hasi.springbatch.readers;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ListReader {

    @Bean
    public ItemReader<String> inMemoryReader() {
        return new ListItemReader<>(Arrays.asList("1", "2", "3"));
    }
}
