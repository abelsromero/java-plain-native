package org.abelsromero.demo;

public class Greeter {

    private static final String TEMPLATE = "Hello %s!!";

    private final String message;

    Greeter(Options options) {
        String message = TEMPLATE.formatted(options.getName());
        if (options.isUppercase()) {
            this.message = message.toUpperCase();
        } else if (options.isLowercase()) {
            this.message = message.toLowerCase();
        } else this.message = message;
    }

    String getMessage() {
        return message;
    }
}
