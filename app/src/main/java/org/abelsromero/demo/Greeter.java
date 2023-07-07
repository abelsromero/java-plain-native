package org.abelsromero.demo;

import java.util.StringJoiner;

public class Greeter {

    private static final String TEMPLATE = "Hello %s!!";

    private final String message;

    Greeter(CliOptions options) {
        String message = TEMPLATE.formatted(options.getName());
        if (options.isUppercase()) {
            message = message.toUpperCase();
        } else if (options.isLowercase()) {
            message = message.toLowerCase();
        }

        StringJoiner stringJoiner = new StringJoiner("\n");
        for (int i = 0; i < options.getRepeat(); i++) {
            stringJoiner.add(message);
        }

        this.message = stringJoiner.toString();
    }

    String getMessage() {
        return message;
    }
}
