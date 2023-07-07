package org.abelsromero.demo;

import com.beust.jcommander.JCommander;

public class CliOptionsParser {

    public JCommander parse(CliOptions options, String[] args) {
        final JCommander jc = JCommander.newBuilder()
                .addObject(options)
                .build();
        jc.parse(args);
        return jc;
    }
}
