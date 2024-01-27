package org.abelsromero.demo;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

class NoneZeroPositiveIntegerValidator implements IParameterValidator {

    public void validate(String name, String value)
            throws ParameterException {
        int n = Integer.parseInt(value);
        if (n <= 0) {
            throw new ParameterException(String.format("Parameter %s should be non-zero positive (found %s)", name, value));
        }
    }
}
