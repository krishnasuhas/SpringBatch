package com.hasi.springbatch.taskletsvschunks.chunks;

import com.hasi.springbatch.taskletsvschunks.model.Line;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

public class NameValidator implements Validator<Line> {
    @Override
    public void validate(Line line) throws ValidationException {
        if (line.getName().startsWith("B")) {
            throw new ValidationException("Name begins with B is invalid " + line.getName());
        }
    }
}
