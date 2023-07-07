package org.abelsromero.demo;

import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThat(outputLines[5]).contains("-r, --repeat");
        assertThat(outputLines[6]).contains("--debug");
        assertThat(outputLines[7]).contains("-h, --help");
    }

    @Test
    void shouldFailWhenAParameterIsInvalid() {
        assertThatThrownBy(() -> App.main(new String[]{"-r", "-1"}))
                .isInstanceOf(ParameterException.class);
    }

    @Test
    void shouldFailWhenRepeatIsOverriddenFromConfigWithAnInvalidValue(@TempDir Path tempDir) throws IOException {
        final Path configFile = createConfigFile(tempDir, """
                config:
                  repeat: -2
                """);

        assertThatThrownBy(() -> App.main(new String[]{"-n", "test", "-r", "2", "-c", configFile.toAbsolutePath().toString()}))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Path createConfigFile(Path tempDir, String config) throws IOException {
        final Path configFile = Files.createFile(tempDir.resolve(String.format("full-config-%s.yaml", UUID.randomUUID())));
        Files.writeString(configFile, config);
        return configFile;
    }
}
