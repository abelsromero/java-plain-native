package org.abelsromero.demo.config;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

// TODO non-zero positive validation for repeat
// TODO consolidate case options in an enum
public class ConfigurationInitializer {

    private ConfigurationInitializer() {

    }

    public static Configuration init(String filePath) {

        final Yaml yaml = new Yaml();
        final FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        final Map<String, Object> data = yaml.load(fileReader);
        if (data == null)
            return new Configuration(Boolean.FALSE, Boolean.FALSE, 1);

        Map<String, Object> configNode = (Map<String, Object>) data.getOrDefault("config", Map.of());
        return new Configuration(
                (Boolean) configNode.getOrDefault("uppercase", Boolean.FALSE),
                (Boolean) configNode.getOrDefault("lowercase", Boolean.FALSE),
                (Integer) configNode.getOrDefault("repeat", 1)
        );
    }
}
