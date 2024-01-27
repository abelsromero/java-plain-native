package org.abelsromero.demo.logging;

public class SysoutLoggingService implements LoggingService {

    @Override
    public void info(String message) {
        System.out.println(message);
    }
}
