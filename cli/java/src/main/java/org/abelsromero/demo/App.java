package org.abelsromero.demo;

import org.abelsromero.demo.cli.CliOptions;
import org.abelsromero.demo.cli.CliOptionsHandler;
import org.abelsromero.demo.cli.impl.JCommanderOptionsHandler;
import org.abelsromero.demo.config.Configuration;
import org.abelsromero.demo.config.ConfigurationInitializer;
import org.abelsromero.demo.config.ConfigurationValidator;
import org.abelsromero.demo.json.MessageFormatter;
import org.abelsromero.demo.logging.LoggingService;
import org.abelsromero.demo.logging.LoggingServiceLocator;

import static org.abelsromero.demo.cli.CliOptionsMerger.merge;


public class App {

    public static void main(String[] args) {
        // TODO make CliOptionsHandler service locator
        final CliOptionsHandler optionsHandler = new JCommanderOptionsHandler();

        final CliOptions options = optionsHandler.parse(args);
        if (options.isHelp()) {
            optionsHandler.help(options);
            return;
        }

        final Configuration config = readConfiguration(options);

        final LoggingService innerLogger = new LoggingServiceLocator().locate(App.class);

        String message = new Greeter(options.getName(), config).getMessage();
        message = new MessageFormatter().format(message, options.getOutputFormat());

        innerLogger.info(message);
        if (config.isDebug() && !options.getParameters().isEmpty())
            innerLogger.info("Ignored parameters: " + options.getParameters());
    }

    /**
     * Create final configuration instance.
     * Takes into consideration: default values and option to pass YML file.
     */
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
