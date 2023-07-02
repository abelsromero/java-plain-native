package cosa.cosa;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CosaTest {

    @Test
    void shouldDoStuff() {
        String value = "uno";

        assertThat(value).isEqualTo("dos");
    }

    @Test
    void shouldDoStuffToo() {
        String value = "uno";

        assertThat(value).isEqualTo("uno");
    }
}
