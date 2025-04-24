package org.abelsromero.demo.sb.args;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class ArgumentsParser {

    public CliOptions parse(ApplicationArguments arguments) {
        return new CliOptions(
            extractName(arguments),
            extractOutputFormat(arguments)
        );
    }

    public String extractName(ApplicationArguments arguments) {
        List<String> values = arguments.getOptionValues("name");
        if (hasValues(values)) {
            return values.get(0);
        }
        return null;
    }

    private OutputFormat extractOutputFormat(ApplicationArguments arguments) {
        List<String> values = arguments.getOptionValues("output");
        if (hasValues(values)) {
            return OutputFormat.valueOf(values.get(0).toUpperCase(Locale.ROOT));
        }
        return null;
    }

    private static boolean hasValues(List<String> values) {
        return values != null && values.size() > 0;
    }

    static void panic(String message) {
        System.out.println("Error: " + message);
        System.exit(1);
    }

}
