package org.abelsromero.demo.config;

public class ConfigurationValidator {

    public void validate(Configuration configuration) throws IllegalArgumentException {
        if (configuration.getRepeat() <= 0)
            throw new IllegalArgumentException("Invalid repeat value: " + configuration.getRepeat());
    }
}
