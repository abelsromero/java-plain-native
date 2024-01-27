package org.abelsromero.demo.process;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ProcessRunnerTest {

    @Test
    void should_run_process_successfully() {
        ProcessResult result = ProcessRunner.run(Arrays.asList("ps"), true);

        assertThat(result.getExitStatus()).isEqualTo(0);
        assertThat(result.getOutputAsString()).isNotBlank();
    }

    @Test
    void should_fail_process() {
        List<String> command = Arrays.asList("in-existent", "/fail");
        Throwable t = catchThrowable(() -> ProcessRunner.run(command, true));

        assertThat(t).hasMessage("Error running process");
        assertThat(t.getCause()).hasMessage("Cannot run program \"in-existent\": error=2, No such file or directory");
    }
}
