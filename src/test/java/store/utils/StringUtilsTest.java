package store.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Test
    void test1() {
        String s = "a,b!c|d";
        String regex = StringUtils.regexSeparators(",", "!", "|");

        String[] split = s.split(regex);
        assertThat(split).hasSize(4);
    }
}
