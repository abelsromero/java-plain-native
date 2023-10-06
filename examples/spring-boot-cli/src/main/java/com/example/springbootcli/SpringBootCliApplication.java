package com.example.springbootcli;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// There's an alternative way with org.springframework.boot.CommandLineRunner,
// but CommandLineRunner simply passes `String[]` instead of parsed arguments.
@SpringBootApplication
public class SpringBootCliApplication implements ApplicationRunner {

    private final ArgumentsParser argumentsParser;

    public SpringBootCliApplication(ArgumentsParser argumentsParser) {
        this.argumentsParser = argumentsParser;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCliApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final String name = argumentsParser.extractName(args);
        if (name.length() == 0)
            System.out.println("Hello World!");
        else
            System.out.println("Hello " + name + "!");
    }
}
