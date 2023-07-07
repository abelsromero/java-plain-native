package org.abelsromero.demo.config;

import java.util.Objects;

/**
 * Note: Mutable Pojo to allow modification since the final configuration is the merge of
 * the yaml and terminal inputs.
 * It doesn't make it easier with snakeyml, since snakeyml does not support enums.
 */
public class Configuration {

    private LetterCase letterCase = LetterCase.NONE;
    private Integer repeat = 1;
    private boolean debug = false;

    public static Configuration defaultConfiguration() {
        return new Configuration();
    }

    public void setLetterCase(LetterCase letterCase) {
        Objects.nonNull(letterCase);
        this.letterCase = letterCase;
    }

    public LetterCase getLetterCase() {
        return letterCase;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public Integer getRepeat() {
        return repeat;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Configuration merge(Configuration configuration) {
        this.letterCase = configuration.letterCase;
        this.repeat = configuration.repeat;
        this.debug = configuration.debug;
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Configuration:")
                .append(String.format("\tLetterCase: %s\n", letterCase))
                .append(String.format("\tRepeat: %s\n", repeat))
                .append(String.format("\tDebug: %s\n", debug))
                .toString();
    }
}
