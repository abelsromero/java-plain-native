package org.abelsromero.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldTest {

    private static final String DEFAULT_OUTPUT = "Hello World\n";

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Nested
    class ReturnsHelloWorld {
        @Test
        void whenArgsAreNull() {
            HelloWorld.main(null);
            assertThat(output.toString()).isEqualTo(DEFAULT_OUTPUT);
        }

        @Test
        void whenArgsAreEmpty() {
            HelloWorld.main(new String[]{});
            assertThat(output.toString()).isEqualTo(DEFAULT_OUTPUT);
        }

        @Test
        void whenFirstArgIsNull() {
            HelloWorld.main(new String[]{null});
            assertThat(output.toString()).isEqualTo(DEFAULT_OUTPUT);
        }

        @Test
        void whenFirstArgIsEmpty() {
            HelloWorld.main(new String[]{""});
            assertThat(output.toString()).isEqualTo(DEFAULT_OUTPUT);
        }
    }

    @Nested
    class ReturnsCommandInput {
        @Test
        void whenIsValid() {
            HelloWorld.main(new String[]{"Monkey"});
            assertThat(output.toString()).isEqualTo("Hello Monkey\n");
        }
    }
}
