package org.abelsromero.demo.json;

import org.abelsromero.demo.cli.OutputFormat;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageFormatterTest {

    @Test
    void shouldFormatAsText() {
        final MessageFormatter formatter = new MessageFormatter();

        String message = formatter.format("Hello World", OutputFormat.TEXT);

        assertThat(message).isEqualTo("Hello World");
    }

    @Test
    void shouldFormatAsJson() {
        final MessageFormatter formatter = new MessageFormatter();

        String message = formatter.format("Hello World", OutputFormat.JSON);

        assertThat(message).isEqualTo("{\"message\":\"Hello World\"}");
    }

}
