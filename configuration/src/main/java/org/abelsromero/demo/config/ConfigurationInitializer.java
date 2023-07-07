package org.abelsromero.demo.config;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

// TODO non-zero positive validation for repeat
public class ConfigurationInitializer {

    private ConfigurationInitializer() {

    }

    public static Configuration load(String filePath) {

        final Yaml yaml = new Yaml();
        final FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        final Map<String, Object> data = yaml.load(fileReader);
        if (data == null)
            return Configuration.defaultConfiguration();

        final var configNode = (Map<String, Object>) data.getOrDefault("config", Map.of());

        final var configuration = new Configuration();
        configuration.setLetterCase(letterCase((String) configNode.get("letter-case")));
        configuration.setRepeat((Integer) configNode.getOrDefault("repeat", 1));

        return configuration;
    }

    private static LetterCase letterCase(String value) {
        if (value == null || value.length() == 0)
            return LetterCase.NONE;
        else
            return LetterCase.valueOf(value.toUpperCase());
    }
}
