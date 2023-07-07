package org.abelsromero.demo.config;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationTest {

    @Test
    void shouldReturnDefaultConfiguration() {
        final Configuration configuration = Configuration.defaultConfiguration();

        assertConfiguration(configuration, LetterCase.NONE, 1, false);
    }

    @Test
    void shouldMergeTheSameConfiguration() {
        final Configuration configuration = Configuration.defaultConfiguration();
        final Configuration toMerge = Configuration.defaultConfiguration();

        configuration.merge(toMerge);

        assertConfiguration(configuration, LetterCase.NONE, 1, false);
    }

    @Test
    void shouldMergeLetterCase() {
        final Configuration configuration = Configuration.defaultConfiguration();
        final Configuration toMerge = Configuration.defaultConfiguration();
        toMerge.setLetterCase(LetterCase.UPPER);

        configuration.merge(toMerge);

        assertConfiguration(configuration, LetterCase.UPPER, 1, false);
    }

    @Test
    void shouldMergeRepeat() {
        final Configuration configuration = Configuration.defaultConfiguration();
        final Configuration toMerge = Configuration.defaultConfiguration();
        toMerge.setRepeat(42);

        configuration.merge(toMerge);

        assertConfiguration(configuration, LetterCase.NONE, 42, false);
    }

    @Test
    void shouldMergeDebug() {
        final Configuration configuration = Configuration.defaultConfiguration();
        final Configuration toMerge = Configuration.defaultConfiguration();
        toMerge.setDebug(true);

        configuration.merge(toMerge);

        assertConfiguration(configuration, LetterCase.NONE, 1, true);
    }

    private static void assertConfiguration(Configuration configuration, LetterCase letterCase, int repeat, boolean debug) {
        assertThat(configuration.getLetterCase()).isEqualTo(letterCase);
        assertThat(configuration.getRepeat()).isEqualTo(repeat);
        assertThat(configuration.isDebug()).isEqualTo(debug);
    }
}
