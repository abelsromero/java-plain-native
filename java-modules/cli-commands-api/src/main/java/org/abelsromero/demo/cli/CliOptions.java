package org.abelsromero.demo.cli;

import java.util.List;

/**
 * Independent class from CLI parsing implementation.
 */
public interface CliOptions {

    List<String> getParameters();

    String getName();

    boolean isUppercase();

    boolean isLowercase();

    int getRepeat();

    boolean isDebug();

    boolean isHelp();

    String getConfigFile();
}
