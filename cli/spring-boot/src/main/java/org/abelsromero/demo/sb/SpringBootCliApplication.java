package org.abelsromero.demo.sb;

import org.abelsromero.demo.sb.json.MessageFormatter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCliApplication implements ApplicationRunner {

    private final ArgumentsParser argumentsParser;
    private final MessageFormatter messageFormatter;

    public SpringBootCliApplication(ArgumentsParser argumentsParser, MessageFormatter messageFormatter) {
        this.argumentsParser = argumentsParser;
        this.messageFormatter = messageFormatter;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCliApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final String name = argumentsParser.extractName(args);

        String message = String.format("Hello %s!", name.length() == 0 ? "World" : name);
        message = messageFormatter.format(message);

        System.out.println(message);
    }
}
