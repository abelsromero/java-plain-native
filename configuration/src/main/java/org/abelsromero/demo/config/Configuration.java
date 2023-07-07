package org.abelsromero.demo.config;

/**
 * Note: Mutable Pojo to allow modification since the final configuration is the merge of
 * the yaml and terminal inputs.
 * It doesn't make it easier with snakeyml, since snakeyml does not support enums.
 */
public class Configuration {

    private LetterCase letterCase;
    private Integer repeat;

    public static Configuration defaultConfiguration() {
        var configuration = new Configuration();
        configuration.letterCase = LetterCase.NONE;
        configuration.repeat = 1;
        return configuration;
    }

    public void setLetterCase(LetterCase letterCase) {
        this.letterCase = letterCase;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public LetterCase getLetterCase() {
        return letterCase;
    }

    public Integer getRepeat() {
        return repeat;
    }

    public Configuration merge(Configuration configuration) {
        if (configuration.letterCase != null)
            this.letterCase = configuration.letterCase;
        if (configuration.repeat != null)
            this.repeat = configuration.repeat;
        return this;
    }
}
