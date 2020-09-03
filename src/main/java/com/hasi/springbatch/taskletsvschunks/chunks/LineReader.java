package com.hasi.springbatch.taskletsvschunks.chunks;

import com.hasi.springbatch.taskletsvschunks.model.Line;
import com.hasi.springbatch.taskletsvschunks.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

public class LineReader implements ItemReader<Line>, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(LineReader.class);
    private FileUtils fu;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        fu = new FileUtils("tasklets-vs-chunks.csv");
        logger.info("Line Reader initialized.");
    }

    @Override
    public Line read() throws Exception {
        Line line = fu.readLine();
        if (line != null) logger.info("Read line: " + line.toString());
        return line;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fu.closeReader();
        logger.info("Line Reader ended.");
        return ExitStatus.COMPLETED;
    }
}
