package org.abelsromero.demo;

import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    private String[] minimalArgs(String value) {
        return new String[]{"-n", "Arthur", value};
    }
}
