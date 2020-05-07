package io.hosuaby.elastic.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class FirstTest {

    @Test
    public void firstTest() {
        var bool = true;
        assertThat(bool)
                .isTrue();
    }
}
