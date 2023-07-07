package org.abelsromero.demo;

import org.abelsromero.demo.config.Configuration;

import java.util.StringJoiner;

public class Greeter {

    private static final String TEMPLATE = "Hello %s!!";

    private final String message;

    Greeter(String name, Configuration options) {
        final String message = switch (options.getLetterCase()) {
            case UPPER -> TEMPLATE.formatted(name).toUpperCase();
            case LOWER -> TEMPLATE.formatted(name).toLowerCase();
            case NONE -> TEMPLATE.formatted(name);
        };

        final StringJoiner stringJoiner = new StringJoiner("\n");
        for (int i = 0; i < options.getRepeat(); i++) {
            stringJoiner.add(message);
        }

        this.message = stringJoiner.toString();
    }

    String getMessage() {
        return message;
    }
}
