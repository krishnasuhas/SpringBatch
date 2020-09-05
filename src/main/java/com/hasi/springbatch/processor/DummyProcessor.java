package com.hasi.springbatch.processor;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DummyProcessor implements ItemProcessor<String, String>, StepExecutionListener {

    private StepExecution stepExecution;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public String process(String s) throws Exception {
        checkIfException(s, "4");
        checkIfException(s, "66");
        return s;
    }

    private void checkIfException(String s, String s2) throws Exception {
        if (s.equals(s2) && !stepExecution.getJobExecution().getExecutionContext().containsKey(s2)) {
            stepExecution.getJobExecution().getExecutionContext().put("4", "present");
            throw new Exception("it's an exception");
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
