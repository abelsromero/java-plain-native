package com.example.springbootcli;

import org.junit.jupiter.api.Test;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgumentsParserTest {

    final ArgumentsParser argumentsParser = new ArgumentsParser();

    @Test
    void shouldReturnEmptyWhenNoArgs() {
        var args = new DefaultApplicationArguments();

        String name = argumentsParser.extractName(args);

        assertThat(name).isEmpty();
    }

    @Test
    void shouldExtractNameInSimpleFormat() {
        var args = new DefaultApplicationArguments("my name", "--cosa=222");

        String name = argumentsParser.extractName(args);

        assertThat(name).isEqualTo("my name");
    }

    @Test
    void shouldExtractNameInOptionsFormat() {
        var args = new DefaultApplicationArguments("--name=my name", "--cosa=222");

        String name = argumentsParser.extractName(args);

        assertThat(name).isEqualTo("my name");
    }
}
