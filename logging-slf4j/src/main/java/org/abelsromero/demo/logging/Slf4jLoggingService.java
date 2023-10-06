package org.abelsromero.demo.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLoggingService implements LoggingService {

    private final Logger logger;

    public Slf4jLoggingService(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }
}
