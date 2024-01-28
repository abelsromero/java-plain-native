package org.abelsromero.demo.cli;

/**
 * Abstract CLI parsing implementation details.
 */
public interface CliOptionsHandler {

    /*
     * Return implementation independent options pojo.
     */
    CliOptions parse(String[] args);


    /**
     * Prints options to console.
     * Note: made 'void' because some implementation simply print it and this is a poc.
     */
    void help(CliOptions options);

}
