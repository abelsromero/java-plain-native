package org.abelsromero.demo.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationInitializerTest {

    @TempDir
    private Path tempDir;

    @Test
    void shouldReadEmptyConfiguration() throws IOException {
        final Path emptyFile = Files.createFile(tempDir.resolve("empty-config.yaml"));

        Configuration init = ConfigurationInitializer.load(emptyFile.toAbsolutePath().toString());

        assertThat(init)
                .usingRecursiveComparison()
                .isEqualTo(configuration(LetterCase.NONE, 1));
    }

    @Test
    void shouldReadFullConfiguration() throws IOException {
        final Path configFile = createConfigFile(tempDir, """
                config:
                  letter-case: upper
                  repeat: 8
                """);

        Configuration config = ConfigurationInitializer.load(configFile.toAbsolutePath().toString());

        assertThat(config)
                .usingRecursiveComparison()
                .isEqualTo(configuration(LetterCase.UPPER, 8));
    }

    @Test
    void shouldReadUppercaseConfiguration() throws IOException {
        final Path configFile = createConfigFile(tempDir, """
                config:
                  letter-case: upper
                """);

        Configuration config = ConfigurationInitializer.load(configFile.toAbsolutePath().toString());

        assertThat(config)
                .usingRecursiveComparison()
                .isEqualTo(configuration(LetterCase.UPPER, 1));
    }

    @Test
    void shouldReadLowercaseConfiguration() throws IOException {
        final Path configFile = createConfigFile(tempDir, """
                config:
                  letter-case: lower
                """);

        Configuration config = ConfigurationInitializer.load(configFile.toAbsolutePath().toString());

        assertThat(config)
                .usingRecursiveComparison()
                .isEqualTo(configuration(LetterCase.LOWER, 1));
    }

    @Test
    void shouldReadNonecaseConfiguration() throws IOException {
        final Path configFile = createConfigFile(tempDir, """
                config:
                  letter-case: none
                """);

        Configuration config = ConfigurationInitializer.load(configFile.toAbsolutePath().toString());

        assertThat(config)
                .usingRecursiveComparison()
                .isEqualTo(configuration(LetterCase.NONE, 1));
    }

    @Test
    void shouldReadRepeatConfiguration() throws IOException {
        final Path configFile = createConfigFile(tempDir, """
                config:
                  repeat: 42
                """);

        Configuration config = ConfigurationInitializer.load(configFile.toAbsolutePath().toString());

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

    private static Path createConfigFile(Path tempDir, String config) throws IOException {
        final Path configFile = Files.createFile(tempDir.resolve(String.format("full-config-%s.yaml", UUID.randomUUID())));
        Files.writeString(configFile, config);
        return configFile;
    }
}
