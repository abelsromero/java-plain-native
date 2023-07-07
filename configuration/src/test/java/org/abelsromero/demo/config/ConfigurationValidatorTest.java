package org.abelsromero.demo.config;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigurationValidatorTest {

    final ConfigurationValidator validator = new ConfigurationValidator();

    @Nested
    class ShouldPass {
        @Test
        void whenConfigurationIsValid() {
            validator.validate(new Configuration());
        }
    }

    @Nested
    class ShouldFail {

        @Test
        void whenRepeatIs0() {
            final var configuration = new Configuration();
            configuration.setRepeat(0);

            assertThatThrownBy(() -> validator.validate(configuration))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid repeat value: 0");
        }

        @Test
        void whenRepeatIsNegative() {
            final var configuration = new Configuration();
            configuration.setRepeat(-1);

            assertThatThrownBy(() -> validator.validate(configuration))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid repeat value: -1");
        }
    }
}
