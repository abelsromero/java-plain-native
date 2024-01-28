package org.abelsromero.demo.cli.impl;

import com.beust.jcommander.ParameterException;
import org.abelsromero.demo.cli.CliOptions;
import org.abelsromero.demo.cli.CliOptionsHandler;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class JCommanderOptionsHandlerTest {

    final CliOptionsHandler optionsHandler = new JCommanderOptionsHandler();

    @Test
    void shouldFailWhenMissingRequiredArguments() {
        final String[] args = {"uno", "dos"};

        Throwable t = catchThrowable(() -> optionsHandler.parse(args));

        assertThat(t).isInstanceOf(ParameterException.class);
    }

    @Test
    void shouldParseMinimumArguments() {
        final String[] args = {"-n", "hello"};

        CliOptions options = optionsHandler.parse(args);

        assertThat(options.getConfigFile()).isNull();
        assertThat(options.getName()).isEqualTo("hello");
        assertThat(options.getParameters()).isEmpty();
        assertThat(options.getRepeat()).isEqualTo(1);
    }

    @Test
    void shouldParseAllArguments() {
        final String[] args = {
            "-n", "hello",
            "-r", "42",
            "-c", "/path/to/configuration.yml"
        };

        CliOptions options = optionsHandler.parse(args);

        assertThat(options.getConfigFile()).isEqualTo("/path/to/configuration.yml");
        assertThat(options.getName()).isEqualTo("hello");
        assertThat(options.getParameters()).isEmpty();
        assertThat(options.getRepeat()).isEqualTo(42);
    }
}
