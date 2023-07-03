package org.abelsromero.demo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @ParameterizedTest
    @ValueSource(strings = {"-h", "--help"})
    void shouldPrintHelp(String helpFlag) {
        var outputStream = new ByteArrayOutputStream();
        var printStream = new PrintStream(outputStream);
        PrintStream outBackup = System.out;
        System.setOut(printStream);

        try {
            App.main(new String[]{helpFlag});
        } catch (RuntimeException e) {

        } finally {
            if (outBackup != null)
                System.setOut(outBackup);
        }

        String[] outputLines = outputStream.toString().split("\n");
        assertThat(outputLines[0]).isEqualTo("Usage: <main class> [options]");
        assertThat(outputLines[2]).contains("-n, --name");
        assertThat(outputLines[3]).contains("-u, --uppercase");
        assertThat(outputLines[4]).contains("-l, --lowercase");
        assertThat(outputLines[6]).contains("-h, --help");
    }
}
