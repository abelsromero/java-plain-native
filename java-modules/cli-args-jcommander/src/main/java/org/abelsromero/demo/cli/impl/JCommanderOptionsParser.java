package org.abelsromero.demo.cli.impl;

import com.beust.jcommander.JCommander;

public class JCommanderOptionsParser {

    public JCommander parse(JCommanderOptions options, String[] args) {
        final JCommander jc = JCommander.newBuilder()
            .addObject(options)
            .build();
        jc.parse(args);
        return jc;
    }
}
