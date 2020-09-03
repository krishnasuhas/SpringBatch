package com.hasi.springbatch.readers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.util.List;

public class StateFullStreamReader implements ItemStreamReader<String> {
    private final List<String> items;
    Logger logger = LoggerFactory.getLogger(StateFullStreamReader.class);
    private int curIndex;
    private boolean restart = false;

    public StateFullStreamReader(List<String> items) {
        this.items = items;
        this.curIndex = 0;
    }


    @Override
    public String read() {
        String item = null;
        if (this.curIndex < this.items.size()) {
            item = this.items.get(this.curIndex);
            logger.info("read string {} at pos {}", item, curIndex);
            this.curIndex++;
        }
        if (this.curIndex == 3 && !restart) {
            throw new RuntimeException("my exception");
        }
        return item;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey("curIndex")) {
            this.curIndex = executionContext.getInt("curIndex");
            this.restart = true;
            logger.info("curIndex present in executionContext");
        } else {
            curIndex = 0;
            executionContext.put("curIndex", curIndex);
            logger.info("curIndex not present in executionContext");
        }
        logger.info("open curIndex {} executionContext", curIndex);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("curIndex", curIndex);
        logger.info("update curIndex {} executionContext", curIndex);
    }

    @Override
    public void close() throws ItemStreamException {
        logger.info("close curIndex {} executionContext", curIndex);
    }
}
