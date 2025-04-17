package org.abelsromero.demo.cli;

public enum OutputFormat {

    TEXT, JSON;

    @Override
    public String toString() {
        // to make JCommander print lower case values in help
        return name().toLowerCase();
    }
}
