package org.abelsromero.demo.cli.impl;

import org.abelsromero.demo.cli.CliOptions;
import org.abelsromero.demo.cli.OutputFormat;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

public class PicocliOptions implements CliOptions {

    @CommandLine.Option(names = {"-p"})
    private List<String> parameters = new ArrayList<>();

    @CommandLine.Option(names = {"-n", "--name"}, required = true, description = "Who to greet", order = 1)
    private String name;

    @CommandLine.Option(names = {"-u", "--uppercase"}, description = "Greet in Uppercase", order = 2)
    private boolean uppercase = false;

    @CommandLine.Option(names = {"-l", "--lowercase"}, description = "Greet in lowercase", order = 3)
    private boolean lowercase = false;

    @CommandLine.Option(names = {"-r", "--repeat"}, description = "Greet many times", order = 4)
    private int repeat = 1;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output format", order = 5)
    private OutputFormat output = OutputFormat.TEXT;

    @CommandLine.Option(names = "--debug", description = "Debug mode", order = 6)
    private boolean debug = false;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Show this message", order = 7)
    private boolean help = false;

    @CommandLine.Option(names = {"-c", "--config-file"}, hidden = true, description = "Configuration file path", order = 4)
    private String configFile;


    @Override
    public List<String> getParameters() {
        return parameters;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isUppercase() {
        return uppercase;
    }

    @Override
    public boolean isLowercase() {
        return lowercase;
    }

    @Override
    public int getRepeat() {
        return repeat;
    }

    @Override
    public boolean isDebug() {
        return debug;
    }

    @Override
    public OutputFormat getOutputFormat() {
        return output;
    }

    @Override
    public boolean isHelp() {
        return help;
    }

    @Override
    public String getConfigFile() {
        return configFile;
    }
}
