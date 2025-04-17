package org.abelsromero.demo.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.abelsromero.demo.cli.OutputFormat;

public class MessageFormatter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String format(String message, OutputFormat outputFormat) {
        return switch (outputFormat) {
            case JSON -> {
                try {
                    yield objectMapper.writeValueAsString(new JsonMessage(message));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            case TEXT -> message;
        };
    }
}
