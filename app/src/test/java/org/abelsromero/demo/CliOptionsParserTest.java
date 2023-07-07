package org.abelsromero.demo;

import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CliOptionsParserTest {

    @Test
    void shouldFailWhenNoArgsArePassed() {
        final CliOptions options = new CliOptions();

        Throwable throwable = catchThrowable(() -> new CliOptionsParser().parse(options, new String[]{}));

        assertThat(throwable).isInstanceOf(ParameterException.class);
    }

    @Test
    void shouldParseName() {
        final CliOptions options = new CliOptions();
        new CliOptionsParser()
                .parse(options, new String[]{"-n", "Arthur"});

        assertThat(options.getName()).isEqualTo("Arthur");
    }

    @Nested
    class WhenParsingUppercaseOption {

        @Test
        void shouldParseShortOption() {
            final CliOptions options = new CliOptions();
            new CliOptionsParser()
                    .parse(options, minimalArgs("-u"));

            assertThat(options.isUppercase()).isTrue();
        }

        @Test
        void shouldParseLongOption() {
            final CliOptions options = new CliOptions();
            new CliOptionsParser()
                    .parse(options, minimalArgs("--uppercase"));

            assertThat(options.isUppercase()).isTrue();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"-l", "--lowercase"})
    void shouldParseLowercaseOption(String option) {
        final CliOptions options = new CliOptions();
        new CliOptionsParser()
                .parse(options, minimalArgs(option));

        assertThat(options.isLowercase()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-r", "--repeat"})
    void shouldParseRepeatOption(String option) {
        final CliOptions options = new CliOptions();
        new CliOptionsParser()
                .parse(options, minimalArgs(option, "4"));

        assertThat(options.getRepeat()).isEqualTo(4);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-c", "--config-file"})
    void shouldParseConfigFileOption(String option) {
        final CliOptions options = new CliOptions();
        new CliOptionsParser()
                .parse(options, minimalArgs(option, "my-settings.yaml"));

        assertThat(options.getConfig()).isEqualTo("my-settings.yaml");
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
