package org.abelsromero.demo;

import org.abelsromero.demo.config.Configuration;
import org.abelsromero.demo.config.LetterCase;

final class CliOptionsMerger {

    private CliOptionsMerger() {
    }

    static Configuration merge(Configuration configuration, CliOptions options) {
        if (options.isUppercase()) {
            configuration.setLetterCase(LetterCase.UPPER);
        }
        if (options.isLowercase()) {
            configuration.setLetterCase(LetterCase.LOWER);
        }
        if (options.getRepeat() > 0) {
            configuration.setRepeat(options.getRepeat());
        }

        configuration.setDebug(options.isDebug());

        return configuration;
    }
}
