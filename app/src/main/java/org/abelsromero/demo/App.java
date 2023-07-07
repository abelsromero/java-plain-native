package org.abelsromero.demo;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.UnixStyleUsageFormatter;
import org.abelsromero.demo.config.Configuration;
import org.abelsromero.demo.config.ConfigurationInitializer;
import org.abelsromero.demo.config.ConfigurationValidator;

import static org.abelsromero.demo.CliOptionsMerger.merge;


public class App {

    public static void main(String[] args) {
        final CliOptions options = new CliOptions();
        final JCommander jc = new CliOptionsParser()
                .parse(options, args);

        if (options.isHelp()) {
            jc.setUsageFormatter(new UnixStyleUsageFormatter(jc));
            jc.usage();
        } else {
            final Configuration config = readConfiguration(options);

            System.out.println(new Greeter(options.getName(), config).getMessage());
            if (config.isDebug() && !options.getParameters().isEmpty())
                System.out.println("Ignored parameters: " + options.getParameters());
        }
    }

    private static Configuration readConfiguration(CliOptions options) {
        var configuration = merge(Configuration.defaultConfiguration(), options);

        if (!isEmpty(options.getConfigFile())) {
            var candidate = ConfigurationInitializer.load(options.getConfigFile());
            if (options.isDebug())
                System.out.println(candidate);

            new ConfigurationValidator()
                    .validate(candidate);

            return configuration.merge(candidate);
        }

        return configuration;
    }

    private static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }
}
