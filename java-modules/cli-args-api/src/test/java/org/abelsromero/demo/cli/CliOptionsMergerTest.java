package org.abelsromero.demo.cli;

import org.abelsromero.demo.config.Configuration;
import org.abelsromero.demo.config.LetterCase;
import org.abelsromero.demo.test.ReflectionMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.abelsromero.demo.cli.CliOptionsMerger.merge;
import static org.assertj.core.api.Assertions.assertThat;

public class CliOptionsMergerTest {

    @Test
    void shouldReturnDefaultConfiguration() {
        final var config = Configuration.defaultConfiguration();
        final var option = ReflectionMock.mock(new TestOptions());

        Configuration configuration = merge(config, option.getInstance());

        assertConfiguration(configuration, LetterCase.NONE, 1, false);
    }

    @Test
    void shouldMergeUpperCase() {
        final var config = Configuration.defaultConfiguration();
        final var option = ReflectionMock.mock(new TestOptions());
        option.mockBoolean("uppercase", true);

        Configuration configuration = merge(config, option.getInstance());

        assertConfiguration(configuration, LetterCase.UPPER, 1, false);
    }

    @Test
    void shouldMergeLowerCase() {
        final var config = Configuration.defaultConfiguration();
        final var option = ReflectionMock.mock(new TestOptions());
        option.mockBoolean("lowercase", true);

        Configuration configuration = merge(config, option.getInstance());

        assertConfiguration(configuration, LetterCase.LOWER, 1, false);
    }

    @Test
    void shouldMergeRepeat() {
        final var config = Configuration.defaultConfiguration();
        final var option = ReflectionMock.mock(new TestOptions());
        option.mockInteger("repeat", 8);

        Configuration configuration = merge(config, option.getInstance());

        assertConfiguration(configuration, LetterCase.NONE, 8, false);
    }

    @Test
    void shouldMergeDebug() {
        final var config = Configuration.defaultConfiguration();
        final var option = ReflectionMock.mock(new TestOptions());
        option.mockBoolean("debug", true);

        Configuration configuration = merge(config, option.getInstance());

        assertConfiguration(configuration, LetterCase.NONE, 1, true);
    }

    private static void assertConfiguration(Configuration configuration, LetterCase letterCase, int repeat, boolean debug) {
        Assertions.assertThat(configuration.getLetterCase()).isEqualTo(letterCase);
        Assertions.assertThat(configuration.getRepeat()).isEqualTo(repeat);
        Assertions.assertThat(configuration.isDebug()).isEqualTo(debug);
    }

    class TestOptions implements CliOptions {

        private List<String> parameters;
        private String name;
        private boolean uppercase;
        private boolean lowercase;
        private int repeat = 1;
        private boolean debug;
        private boolean help;
        private String configFile;

        @Override
        public List<String> getParameters() {
            return parameters;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isUppercase() {
            return uppercase;
        }

        @Override
        public boolean isLowercase() {
            return lowercase;
        }

        @Override
        public int getRepeat() {
            return repeat;
        }

        @Override
        public boolean isDebug() {
            return debug;
        }

        @Override
        public boolean isHelp() {
            return help;
        }

        @Override
        public String getConfigFile() {
            return configFile;
        }
    }
}
