package org.abelsromero.demo.cli.impl;

import org.abelsromero.demo.cli.CliOptions;
import org.abelsromero.demo.cli.CliOptionsHandler;

public class DemoRunner {

    public static void main(String[] args) {
        CliOptionsHandler optionsHandler = new JCommanderOptionsHandler();
        CliOptions options = optionsHandler.parse(args);

        System.out.println("Name: " + options.getName());
        System.out.println("Repeat: " + options.getRepeat());
        System.out.println("Help: " + options.isHelp());

        System.out.println("Done");
        if (options.isHelp()) {
            optionsHandler.help(options);
        }
    }
}
