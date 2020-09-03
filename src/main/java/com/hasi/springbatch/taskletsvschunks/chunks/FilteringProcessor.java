package com.hasi.springbatch.taskletsvschunks.chunks;

import com.hasi.springbatch.taskletsvschunks.model.Line;
import org.springframework.batch.item.ItemProcessor;

public class FilteringProcessor implements ItemProcessor<Line, Line> {
    @Override
    public Line process(Line line) {
        if (line.getName().startsWith("B")) {
            return null;
        }
        return line;
    }
}
