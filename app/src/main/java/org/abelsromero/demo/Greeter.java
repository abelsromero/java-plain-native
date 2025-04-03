package org.abelsromero.demo;

import org.abelsromero.demo.config.Configuration;

import java.util.Locale;
import java.util.StringJoiner;

public class Greeter {

    private static final String TEMPLATE = "Hello %s!!";

    private final String message;

    Greeter(String name, Configuration options) {
        /* java 17+
        final String message = switch (options.getLetterCase()) {
            case UPPER -> TEMPLATE.formatted(name).toUpperCase();
            case LOWER -> TEMPLATE.formatted(name).toLowerCase();
            case NONE -> TEMPLATE.formatted(name);
        };
        */
        String message = String.format(TEMPLATE, name);
        switch (options.getLetterCase()) {
            case UPPER:
                message = message.toUpperCase(Locale.ROOT);
                break;
            case LOWER:
                message = message.toLowerCase(Locale.ROOT);
                break;
        }

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
