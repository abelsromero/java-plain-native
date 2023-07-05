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

class OptionsParserTest {

    @Test
    void shouldFailWhenNoArgsArePassed() {
        final Options options = new Options();

        Throwable throwable = catchThrowable(() -> new OptionsParser().parse(options, new String[]{}));

        assertThat(throwable).isInstanceOf(ParameterException.class);
    }

    @Test
    void shouldParseName() {
        final Options options = new Options();
        new OptionsParser()
                .parse(options, new String[]{"-n", "Arthur"});

        assertThat(options.getName()).isEqualTo("Arthur");
    }

    @Nested
    class WhenParsingUppercaseOption {

        @Test
        void shouldParseShortOption() {
            final Options options = new Options();
            new OptionsParser()
                    .parse(options, minimalArgs("-u"));

            assertThat(options.isUppercase()).isTrue();
        }

        @Test
        void shouldParseLongOption() {
            final Options options = new Options();
            new OptionsParser()
                    .parse(options, minimalArgs("--uppercase"));

            assertThat(options.isUppercase()).isTrue();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"-l", "--lowercase"})
    void shouldParseLowercaseOption(String option) {
        final Options options = new Options();
        new OptionsParser()
                .parse(options, minimalArgs(option));

        assertThat(options.isLowercase()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-r", "--repeat"})
    void shouldParseRepeatOption(String option) {
        final Options options = new Options();
        new OptionsParser()
                .parse(options, minimalArgs(option, "4"));

        assertThat(options.getRepeat()).isEqualTo(4);
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
