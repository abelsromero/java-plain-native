package org.abelsromero.demo.cli.impl;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.UnixStyleUsageFormatter;
import org.abelsromero.demo.cli.CliOptions;
import org.abelsromero.demo.cli.CliOptionsHandler;

public class JCommanderOptionsHandler implements CliOptionsHandler {

    private JCommander jc;

    @Override
    public CliOptions parse(String[] args) {
        final JCommanderOptions options = new JCommanderOptions();

        this.jc = new JCommanderOptionsParser()
            .parse(options, args);

        return options;
    }

    @Override
    public void help(CliOptions options) {
        jc.setUsageFormatter(new UnixStyleUsageFormatter(jc));
        jc.usage();
    }
}
