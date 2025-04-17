package org.abelsromero.demo.cli.impl;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import org.abelsromero.demo.cli.CliOptions;
import org.abelsromero.demo.cli.OutputFormat;

import java.util.ArrayList;
import java.util.List;

public final class JCommanderOptions implements CliOptions {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"-n", "--name"}, required = true, description = "Who to greet", order = 1)
    private String name;

    @Parameter(names = {"-u", "--uppercase"}, description = "Greet in Uppercase", order = 2)
    private boolean uppercase = false;

    @Parameter(names = {"-l", "--lowercase"}, description = "Greet in lowercase", order = 3)
    private boolean lowercase = false;

    @Parameter(names = {"-r", "--repeat"}, validateWith = PositiveInteger.class, description = "Greet many times", order = 4)
    private int repeat = 1;

    @Parameter(names = {"-o", "--output"}, description = "Output format", order = 5)
    private OutputFormat output = OutputFormat.TEXT;

    @Parameter(names = "--debug", description = "Debug mode", order = 6)
    private boolean debug = false;

    @Parameter(names = {"-h", "--help"}, help = true, description = "Show this message", order = 7)
    private boolean help = false;

    @Parameter(names = {"-c", "--config-file"}, hidden = true, description = "Configuration file path", order = 4)
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
