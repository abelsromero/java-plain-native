package org.abelsromero.demo.cli.impl;

import org.abelsromero.demo.cli.CliOptions;

public class DemoRunner {

    public static void main(String[] args) {
        PicocliOptionsHandler optionsHandler = new PicocliOptionsHandler();
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
