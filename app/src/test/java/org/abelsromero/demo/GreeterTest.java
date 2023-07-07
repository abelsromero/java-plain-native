package org.abelsromero.demo;

import org.abelsromero.demo.config.Configuration;
import org.abelsromero.demo.config.LetterCase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GreeterTest {

    private static final String NAME = "Arthur";

    @Test
    void shouldGreet() {
        final var config = Configuration.defaultConfiguration();

        final String message = new Greeter(NAME, config).getMessage();

        assertThat(message).isEqualTo("Hello Arthur!!");
    }

    @Test
    void shouldGreetInUppercase() {
        final var config = configuration(LetterCase.UPPER);

        final String message = new Greeter(NAME, config).getMessage();

        assertThat(message).isEqualTo("HELLO ARTHUR!!");
    }

    @Test
    void shouldGreetInLowercase() {
        final var config = configuration(LetterCase.LOWER);

        final String message = new Greeter(NAME, config).getMessage();

        assertThat(message).isEqualTo("hello arthur!!");
    }

    @Test
    void shouldGreetWithRepeats() {
        final var config = configuration(4);

        final String message = new Greeter(NAME, config).getMessage();

        assertThat(message).isEqualTo("Hello Arthur!!\n" + "Hello Arthur!!\n" + "Hello Arthur!!\n" + "Hello Arthur!!");
    }

    public static Configuration configuration(LetterCase letterCase) {
        Configuration configuration = new Configuration();
        configuration.setLetterCase(letterCase);
        return configuration;
    }

    public static Configuration configuration(int repeat) {
        Configuration configuration = new Configuration();
        configuration.setRepeat(repeat);
        return configuration;
    }
}
