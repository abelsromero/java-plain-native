package org.abelsromero.demo.sb;

import org.abelsromero.demo.sb.args.ArgumentsParser;
import org.abelsromero.demo.sb.args.CliOptions;
import org.abelsromero.demo.sb.json.MessageFormatter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

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
        final CliOptions options = argumentsParser.parse(args);

        String message = String.format("Hello %s!", StringUtils.hasLength(options.name()) ? options.name() : "World");

        if (options.outputFormat() != null) {
            message = switch (options.outputFormat()) {
                case JSON -> messageFormatter.format(message);
                default -> message;
            };
        }

        System.out.println(message);
    }
}
