package org.abelsromero.demo.cli.impl;

import org.abelsromero.demo.cli.CliOptions;
import org.abelsromero.demo.cli.CliOptionsHandler;
import org.abelsromero.demo.cli.OutputFormat;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PicocliOptionsHandlerTest {

    @Nested
    class WhenParsing {

        @Test
        void shouldFailWhenMissingRequiredArguments() {
            final CliOptionsHandler optionsHandler = new PicocliOptionsHandler();
            final String[] args = {"uno", "dos"};

            Throwable t = catchThrowable(() -> optionsHandler.parse(args));

            assertThat(t).isInstanceOf(CommandLine.MissingParameterException.class);
        }

        @Test
        void shouldParseMinimumArguments() {
            final CliOptionsHandler optionsHandler = new PicocliOptionsHandler();
            final String[] args = {"-n", "hello"};

            CliOptions options = optionsHandler.parse(args);

            assertThat(options.getConfigFile()).isNull();
            assertThat(options.getName()).isEqualTo("hello");
            assertThat(options.getParameters()).isEmpty();
            assertThat(options.getRepeat()).isEqualTo(1);
        }

        @Test
        void shouldParseAllArguments() {
            final CliOptionsHandler optionsHandler = new PicocliOptionsHandler();
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

        @Test
        void shouldParseName() {
            final CliOptionsHandler optionsHandler = new PicocliOptionsHandler();
            final String[] args = {"-n", "Arthur"};

            CliOptions options = optionsHandler.parse(args);

            assertThat(options.getName()).isEqualTo("Arthur");
        }

        @ParameterizedTest
        @ValueSource(strings = {"-u", "--uppercase"})
        void shouldParseUppercaseOption(String option) {
            final CliOptionsHandler optionsHandler = new PicocliOptionsHandler();
            final String[] args = minimalArgs(option);

            CliOptions options = optionsHandler.parse(args);

            assertThat(options.isUppercase()).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"-l", "--lowercase"})
        void shouldParseLowercaseOption(String option) {
            final CliOptionsHandler optionsHandler = new PicocliOptionsHandler();
            final String[] args = minimalArgs(option);

            CliOptions options = optionsHandler.parse(args);

            assertThat(options.isLowercase()).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"-r", "--repeat"})
        void shouldParseRepeatOption(String option) {
            final CliOptionsHandler optionsHandler = new PicocliOptionsHandler();
            final String[] args = minimalArgs(option, "4");

            CliOptions options = optionsHandler.parse(args);

            assertThat(options.getRepeat()).isEqualTo(4);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-o", "--output"})
        void shouldParseOutputFormat(String option) {
            final CliOptionsHandler optionsHandler = new PicocliOptionsHandler();
            final String[] args = minimalArgs(option, "json");

            CliOptions options = optionsHandler.parse(args);

            assertThat(options.getOutputFormat()).isEqualTo(OutputFormat.JSON);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-c", "--config-file"})
        void shouldParseConfigFileOption(String option) {
            final CliOptionsHandler optionsHandler = new PicocliOptionsHandler();
            final String[] args = minimalArgs(option, "my-settings.yaml");

            CliOptions options = optionsHandler.parse(args);

            assertThat(options.getConfigFile()).isEqualTo("my-settings.yaml");
        }

        private String[] minimalArgs(String... values) {
            final List<String> args = new ArrayList<>();
            args.add("-n");
            args.add("Arthur");
            for (String value : values)
                args.add(value);
            return args.toArray(new String[args.size()]);
        }
    }
}
