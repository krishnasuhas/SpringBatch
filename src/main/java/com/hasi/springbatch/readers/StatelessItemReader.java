package com.hasi.springbatch.readers;

import org.springframework.batch.item.ItemReader;

import java.util.Iterator;
import java.util.List;

public class StatelessItemReader implements ItemReader<String> {
    private final Iterator<String> data;

    public StatelessItemReader(List<String> data) {
        this.data = data.iterator();
    }


    @Override
    public String read() {
        if (this.data.hasNext()) {
            return this.data.next();
        } else {
//            return "Never Ending"; will never end as you not returning null
            return null;
        }
    }
}
