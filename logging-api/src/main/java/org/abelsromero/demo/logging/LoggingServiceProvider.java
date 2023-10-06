package org.abelsromero.demo.logging;

public interface LoggingServiceProvider {

    LoggingService create(Class<?> clazz);

}
