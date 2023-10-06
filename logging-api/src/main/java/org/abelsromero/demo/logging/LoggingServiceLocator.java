package org.abelsromero.demo.logging;

import java.util.Optional;
import java.util.ServiceLoader;

public class LoggingServiceLocator {

    public static final SysoutLoggingService DEFAULT_SERVICE = new SysoutLoggingService();

    public LoggingService locate(Class<?> clazz) {
        Optional<LoggingServiceProvider> first = ServiceLoader.load(LoggingServiceProvider.class)
            .findFirst();
        return first
            .map(provider -> provider.create(clazz))
            .orElse(DEFAULT_SERVICE);
    }
}
