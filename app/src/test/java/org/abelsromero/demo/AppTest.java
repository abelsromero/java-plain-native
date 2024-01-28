package org.abelsromero.demo;

import org.abelsromero.demo.test.FilesHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchException;


@Disabled("native issues")
public class AppTest {

    @TempDir
    private static Path tempDir;

    private static FilesHandler filesHandler;

    @BeforeAll
    static void setup() {
        filesHandler = new FilesHandler(tempDir);
    }

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
        assertThat(outputLines[5]).contains("-r, --repeat");
        assertThat(outputLines[6]).contains("--debug");
        assertThat(outputLines[7]).contains("-h, --help");
    }

    @Test
    void shouldFailWhenAParameterIsInvalid() {
        Exception e = catchException(() -> App.main(new String[]{"-r", "-1"}));
        assertThat(e.getClass().getName())
            .isEqualTo("com.beust.jcommander.ParameterException");
    }

    @Test
    void shouldFailWhenRepeatIsOverriddenFromConfigWithAnInvalidValue() throws IOException {
        final Path configFile = filesHandler.createFile("config:\n  repeat: -2\n");

        assertThatThrownBy(() -> App.main(new String[]{"-n", "test", "-r", "2", "-c", configFile.toAbsolutePath().toString()}))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
