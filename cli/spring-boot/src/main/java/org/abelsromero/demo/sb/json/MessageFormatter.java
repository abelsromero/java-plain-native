package org.abelsromero.demo.sb.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class MessageFormatter {

    private final ObjectMapper objectMapper;

    MessageFormatter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String format(String message) {
        try {
            return objectMapper.writeValueAsString(new Message(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
