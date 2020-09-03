package com.hasi.springbatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class ChunkListener {
    Logger logger = LoggerFactory.getLogger(ChunkListener.class);

    @BeforeChunk
    public void beforeChunk(ChunkContext context) {
        logger.info(">> Before the chunck {}", context.getStepContext().getStepName());
    }

    @AfterChunk
    public void afterChunk(ChunkContext context) {
        logger.info("<< After the chunck {}", context.getStepContext().getStepName());
    }
}
