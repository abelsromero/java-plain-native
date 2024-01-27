package org.abelsromero.demo.process;

import org.abelsromero.demo.logging.LoggingService;
import org.abelsromero.demo.logging.LoggingServiceLocator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class ProcessRunner {

    private static final LoggingService logger = new LoggingServiceLocator().locate(ProcessRunner.class);

    private ProcessRunner() {
    }

    public static ProcessResult run(List<String> command, boolean failOnError) {
        return run(command, Collections.emptyMap(), failOnError);
    }

    private static ProcessResult run(List<String> command, Map<String, String> environment, boolean failOnError) {
        try {
            return runInterruptably(command, environment, failOnError);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            if (failOnError) {
                throw new RuntimeException("Interrupted whilst waiting for '" + String.join(" ", command) + "'", e);
            }
            return null;
        }
    }

    private static ProcessResult runInterruptably(List<String> command, Map<String, String> environment,
                                           boolean failOnError) throws InterruptedException {

        Process process = null;
        Path outputRedirect = null;

        try {
            outputRedirect = Files.createTempFile("process_output", ".out");
            logger.info(String.format("Executing command: %s", String.join(" ", command)));
            ProcessBuilder processBuilder = new ProcessBuilder()
                .redirectErrorStream(true)
                .redirectOutput(outputRedirect.toFile())
                .command(command);
            Map<String, String> processEnv = processBuilder.environment();
            processEnv.putAll(environment);
            process = processBuilder.start();
            process.waitFor(5, TimeUnit.MINUTES);
            int exitValue = process.exitValue();
            if (exitValue != 0 && failOnError) {
                final String output = getOutput(outputRedirect);
                throw new RuntimeException(
                    String.format("Error running command '%s' - exit value of: %s. Output: %s",
                        String.join(" ", command),
                        exitValue,
                        output));
            }
            return new ProcessResult(process, outputRedirect);
        } catch (IllegalThreadStateException stateException) {
            if (process != null) {
                throw new RuntimeException(
                    String.format("Timeout reached for %s. Output: %s",
                        String.join(" ", command),
                        getOutput(outputRedirect)));
            } else {
                throw new RuntimeException(String.format("Could not start: %s", String.join(" ", command)));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error running process", e);
        }
    }

    private static String getOutput(Path processOutput) {
        try {
            return "\n" + Files.readString(processOutput);
        } catch (IOException e) {
            return couldNotReadProcessOutput(processOutput);
        }
    }

    private static String couldNotReadProcessOutput(Path processOutput) {
        return "Could not read: " + processOutput.getFileName();
    }
}
