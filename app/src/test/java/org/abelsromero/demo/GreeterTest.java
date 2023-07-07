package org.abelsromero.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GreeterTest {

    @Test
    void shouldGreet() {
        final var options = options();

        final String message = new Greeter(options.getInstance()).getMessage();

        assertThat(message).isEqualTo("Hello Arthur!!");
    }

    @Test
    void shouldGreetInUppercase() {
        final var options = options();
        options.mockBoolean("uppercase", Boolean.TRUE);

        final String message = new Greeter(options.getInstance()).getMessage();

        assertThat(message).isEqualTo("HELLO ARTHUR!!");
    }

    @Test
    void shouldGreetInLowercase() {
        final var options = options();
        options.mockBoolean("lowercase", Boolean.TRUE);

        final String message = new Greeter(options.getInstance()).getMessage();

        assertThat(message).isEqualTo("hello arthur!!");
    }

    @Test
    void shouldGreetWithRepeats() {
        final var options = options();
        options.mockInteger("repeat", 4);

        final String message = new Greeter(options.getInstance()).getMessage();

        assertThat(message).isEqualTo("Hello Arthur!!\n" + "Hello Arthur!!\n" + "Hello Arthur!!\n" + "Hello Arthur!!");
    }

    public static ReflectionMock<CliOptions> options() {
        final CliOptions options = new CliOptions();
        final ReflectionMock<CliOptions> mock = ReflectionMock.mock(options);
        mock.mockString("name", "Arthur");
        return mock;
    }
}
