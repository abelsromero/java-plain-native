package org.abelsromero.demo.sb.args;


import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

import static org.assertj.core.api.Assertions.assertThat;

class ArgumentsParserTest {

    @Test
    void shouldParseName() {
        final var parser = new ArgumentsParser();
        var args = arguments("--name=a-name");

        CliOptions options = parser.parse(args);
        String name = options.name();
        OutputFormat outputFormat = options.outputFormat();

        assertThat(name).isEqualTo("a-name");
        assertThat(outputFormat).isNull();
    }

    @Test
    void shouldParseOutput() {
        final var parser = new ArgumentsParser();
        var args = arguments("--output=json");

        CliOptions options = parser.parse(args);
        String name = options.name();
        OutputFormat outputFormat = options.outputFormat();

        assertThat(name).isNull();
        assertThat(outputFormat).isEqualTo(OutputFormat.JSON);
    }

    private static DefaultApplicationArguments arguments(String... args) {
        return new DefaultApplicationArguments(args);
    }
}
