package org.abelsromero.demo;

public class HelloWorld {

    private static final String DEFAULT_NAME = "World";

    public static void main(String[] args) {
        System.out.println("Hello " + extractValue(args));
    }

    private static String extractValue(String[] values) {
        return values == null || values.length == 0 ? DEFAULT_NAME : extractValue(values[0]);
    }

    private static String extractValue(String value) {
        return value == null || value.length() == 0 ? DEFAULT_NAME : value;
    }
}
