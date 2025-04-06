package com.example.springbootshell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Locale;

@ShellComponent
public class SayHello {

    // arity=1, ignores arguments past the first
    @ShellMethod(key = "hello", value = "Who to greet")
    public String greet(@ShellOption(arity = 1) String arg) {
        return "Hello %s!".formatted(arg);
    }

    @ShellMethod(key = "uppercase", value = "Greet in Uppercase")
    public String uppercase(@ShellOption String arg) {
        return "Hello %s!".formatted(arg.toUpperCase(Locale.ROOT));
    }

    @ShellMethod(key = "lowercase", value = "Greet in lowercase")
    public String lowercase(@ShellOption String arg) {
        return "Hello %s!".formatted(arg.toLowerCase(Locale.ROOT));
    }
}
