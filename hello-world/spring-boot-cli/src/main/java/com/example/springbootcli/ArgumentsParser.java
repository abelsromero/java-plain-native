package com.example.springbootcli;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ArgumentsParser {

    public String extractName(List<String> arguments) {
        return arguments.size() > 0
                ? arguments.get(0)
                : "";
    }

    public String extractName(ApplicationArguments arguments) {
        List<String> nameValues = arguments.getOptionValues("name");
        return (nameValues != null && nameValues.size() > 0)
                ? nameValues.get(0)
                : extractName(arguments.getNonOptionArgs());
    }
}
