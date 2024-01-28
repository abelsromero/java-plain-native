package org.abelsromero.demo.cli.impl;

import org.abelsromero.demo.cli.CliOptions;
import org.abelsromero.demo.cli.CliOptionsHandler;
import picocli.CommandLine;

public class PicocliOptionsHandler implements CliOptionsHandler {

    private CommandLine commandLine;

    @Override
    public CliOptions parse(String[] args) {
        final PicocliOptions options = new PicocliOptions();

        this.commandLine = new CommandLine(options);
        commandLine.parseArgs(args);

        return options;
    }

    @Override
    public void help(CliOptions options) {
        commandLine.usage(System.out);
    }
}
