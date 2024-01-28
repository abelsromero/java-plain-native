package org.abelsromero.demo.cli.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JCommanderOptionsParserTest {


    @Test
    void shouldParseName() {
        final JCommanderOptions options = new JCommanderOptions();
        new JCommanderOptionsParser()
                .parse(options, new String[]{"-n", "Arthur"});

        assertThat(options.getName()).isEqualTo("Arthur");
    }

    @Nested
    class WhenParsingUppercaseOption {

        @Test
        void shouldParseShortOption() {
            final JCommanderOptions options = new JCommanderOptions();
            new JCommanderOptionsParser()
                    .parse(options, minimalArgs("-u"));

            assertThat(options.isUppercase()).isTrue();
        }

        @Test
        void shouldParseLongOption() {
            final JCommanderOptions options = new JCommanderOptions();
            new JCommanderOptionsParser()
                    .parse(options, minimalArgs("--uppercase"));

            assertThat(options.isUppercase()).isTrue();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"-l", "--lowercase"})
    void shouldParseLowercaseOption(String option) {
        final JCommanderOptions options = new JCommanderOptions();
        new JCommanderOptionsParser()
                .parse(options, minimalArgs(option));

        assertThat(options.isLowercase()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-r", "--repeat"})
    void shouldParseRepeatOption(String option) {
        final JCommanderOptions options = new JCommanderOptions();
        new JCommanderOptionsParser()
                .parse(options, minimalArgs(option, "4"));

        assertThat(options.getRepeat()).isEqualTo(4);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-c", "--config-file"})
    void shouldParseConfigFileOption(String option) {
        final JCommanderOptions options = new JCommanderOptions();
        new JCommanderOptionsParser()
                .parse(options, minimalArgs(option, "my-settings.yaml"));

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
