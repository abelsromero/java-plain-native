package org.abelsromero.demo;

import org.abelsromero.demo.config.Configuration;
import org.abelsromero.demo.config.LetterCase;
import org.junit.jupiter.api.Test;

import static org.abelsromero.demo.CliOptionsMerger.merge;
import static org.assertj.core.api.Assertions.assertThat;

public class CliOptionsMergerTest {

    @Test
    void shouldReturnDefaultConfiguration() {
        final var config = Configuration.defaultConfiguration();
        final var option = ReflectionMock.mock(new CliOptions());

        Configuration configuration = merge(config, option.getInstance());

        assertConfiguration(configuration, LetterCase.NONE, 1, false);
    }

    @Test
    void shouldMergeUpperCase() {
        final var config = Configuration.defaultConfiguration();
        final var option = ReflectionMock.mock(new CliOptions());
        option.mockBoolean("uppercase", true);

        Configuration configuration = merge(config, option.getInstance());

        assertConfiguration(configuration, LetterCase.UPPER, 1, false);
    }

    @Test
    void shouldMergeLowerCase() {
        final var config = Configuration.defaultConfiguration();
        final var option = ReflectionMock.mock(new CliOptions());
        option.mockBoolean("lowercase", true);

        Configuration configuration = merge(config, option.getInstance());

        assertConfiguration(configuration, LetterCase.LOWER, 1, false);
    }

    @Test
    void shouldMergeRepeat() {
        final var config = Configuration.defaultConfiguration();
        final var option = ReflectionMock.mock(new CliOptions());
        option.mockInteger("repeat", 8);

        Configuration configuration = merge(config, option.getInstance());

        assertConfiguration(configuration, LetterCase.NONE, 8, false);
    }

    @Test
    void shouldMergeDebug() {
        final var config = Configuration.defaultConfiguration();
        final var option = ReflectionMock.mock(new CliOptions());
        option.mockBoolean("debug", true);

        Configuration configuration = merge(config, option.getInstance());

        assertConfiguration(configuration, LetterCase.NONE, 1, true);
    }

    private static void assertConfiguration(Configuration configuration, LetterCase letterCase, int repeat, boolean debug) {
        assertThat(configuration.getLetterCase()).isEqualTo(letterCase);
        assertThat(configuration.getRepeat()).isEqualTo(repeat);
        assertThat(configuration.isDebug()).isEqualTo(debug);
    }
}
