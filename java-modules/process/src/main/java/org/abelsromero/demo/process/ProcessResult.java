package org.abelsromero.demo.process;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProcessResult {

    private final Process process;
    private final Path outputFile;

    public ProcessResult(Process process, Path outputFile) {
        this.process = process;
        this.outputFile = outputFile;
    }

    public int getExitStatus() {
        return process.exitValue();
    }

    public String getOutputAsString() {
        try {
            return Files.readString(outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
