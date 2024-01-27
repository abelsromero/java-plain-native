package org.abelsromero.demo.config;

import org.abelsromero.demo.test.FilesHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.abelsromero.demo.config.ConfigurationInitializer.load;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationInitializerTest {

    @TempDir
    private static Path tempDir;

    private static FilesHandler filesHandler;

    @BeforeAll
    static void setup() {
        filesHandler = new FilesHandler(tempDir);
    }

    @Test
    void shouldReadEmptyConfiguration() throws IOException {
        final Path emptyFile = Files.createFile(tempDir.resolve("empty-config.yaml"));

        Configuration init = load(emptyFile.toAbsolutePath().toString());

        assertThat(init)
            .usingRecursiveComparison()
            .isEqualTo(configuration(LetterCase.NONE, 1));
    }

    @Test
    void shouldReadFullConfiguration() throws IOException {
        final Path configFile = filesHandler.createFile(configFile("letter-case: upper", "repeat: 8"));

        Configuration config = load(configFile.toAbsolutePath().toString());

        assertThat(config)
            .usingRecursiveComparison()
            .isEqualTo(configuration(LetterCase.UPPER, 8));
    }

    @Test
    void shouldReadUppercaseConfiguration() throws IOException {
        final Path configFile = filesHandler.createFile(configFile("letter-case: upper"));

        Configuration config = load(configFile.toAbsolutePath().toString());

        assertThat(config)
            .usingRecursiveComparison()
            .isEqualTo(configuration(LetterCase.UPPER, 1));
    }

    @Test
    void shouldReadLowercaseConfiguration() throws IOException {
        final Path configFile = filesHandler.createFile(configFile("letter-case: lower"));

        Configuration config = load(configFile.toAbsolutePath().toString());

        assertThat(config)
            .usingRecursiveComparison()
            .isEqualTo(configuration(LetterCase.LOWER, 1));
    }

    @Test
    void shouldReadNonecaseConfiguration() throws IOException {
        final Path configFile = filesHandler.createFile(configFile("letter-case: none"));

        Configuration config = load(configFile.toAbsolutePath().toString());

        assertThat(config)
            .usingRecursiveComparison()
            .isEqualTo(configuration(LetterCase.NONE, 1));
    }

    @Test
    void shouldReadRepeatConfiguration() throws IOException {
        final Path configFile = filesHandler.createFile(configFile("repeat: 42"));

        Configuration config = load(configFile.toAbsolutePath().toString());

        assertThat(config)
            .usingRecursiveComparison()
            .isEqualTo(configuration(LetterCase.NONE, 42));
    }

    private Object configuration(LetterCase letterCase, int repeat) {
        final var config = new Configuration();
        config.setLetterCase(letterCase);
        config.setRepeat(repeat);
        return config;
    }

    private String configFile(String... rows) {
        final StringBuilder sb = new StringBuilder("config:\n");
        for (String row : rows) {
            sb.append("  " + row);
            sb.append("\n");
        }
        return sb.toString();
    }
}
