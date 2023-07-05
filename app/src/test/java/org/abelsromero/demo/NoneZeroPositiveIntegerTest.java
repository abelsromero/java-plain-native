package org.abelsromero.demo;

import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class NoneZeroPositiveIntegerTest {

    private static final String PARAM_NAME = "repeat";

    @Test
    void shouldFailWithZero() {
        var validator = new NoneZeroPositiveInteger();

        Throwable t = catchThrowable(() -> validator.validate(PARAM_NAME, "0"));

        assertThat(t)
                .isInstanceOf(ParameterException.class)
                .hasMessage("Parameter repeat should be non-zero positive (found 0)");
    }

    @Test
    void shouldFailWithNegativeValue() {
        var validator = new NoneZeroPositiveInteger();

        Throwable t = catchThrowable(() -> validator.validate(PARAM_NAME, "-10"));

        assertThat(t)
                .isInstanceOf(ParameterException.class)
                .hasMessage("Parameter repeat should be non-zero positive (found -10)");
    }

    @Test
    void shouldValidateAPositiveValue() {
        var validator = new NoneZeroPositiveInteger();

        validator.validate(PARAM_NAME, "42");
    }
}
