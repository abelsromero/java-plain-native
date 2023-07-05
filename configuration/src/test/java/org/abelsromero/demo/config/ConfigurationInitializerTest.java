package org.abelsromero.demo.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigurationInitializerTest {


    @Test
    void shouldReadEmptyConfiguration(@TempDir Path tempDir) throws IOException {
        final Path emptyFile = Files.createFile(tempDir.resolve("empty-config.yaml"));

        Configuration init = ConfigurationInitializer.init(emptyFile.toAbsolutePath().toString());

        assertThat(init)
                .isEqualTo(new Configuration(false, false, 1));
    }

    @Test
    void shouldReadFullConfiguration(@TempDir Path tempDir) throws IOException {
        final Path configFile = createConfigFile(tempDir, """
                config:
                  uppercase: true
                  lowercase: true
                  repeat: 8
                """);

        Configuration init = ConfigurationInitializer.init(configFile.toAbsolutePath().toString());

        assertThat(init)
                .isEqualTo(new Configuration(true, true, 8));
    }

    @Test
    void shouldReadPartialConfiguration(@TempDir Path tempDir) throws IOException {
        final Path configFile = createConfigFile(tempDir, """
                config:
                  uppercase: true
                  repeat: 1
                """);

        Configuration init = ConfigurationInitializer.init(configFile.toAbsolutePath().toString());

        assertThat(init)
                .isEqualTo(new Configuration(true, false, 1));
    }

    private static Path createConfigFile(Path tempDir, String config) throws IOException {
        final Path configFile = Files.createFile(tempDir.resolve("full-config.yaml"));
        Files.writeString(configFile, config);
        return configFile;
    }
}
