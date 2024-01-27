package org.abelsromero.demo.logging;

public class Slf4jLoggingServiceProvider implements LoggingServiceProvider {
    @Override
    public LoggingService create(Class<?> clazz) {
        return new Slf4jLoggingService(clazz);
    }
}
