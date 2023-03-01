package com.assignment.login.intra.text.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.assignment.login.infra.text.util.RandomGenerator.rand;
import static org.assertj.core.api.Assertions.assertThat;

public class RandomGeneratorTest {
    @Test
    @DisplayName("6자리의 숫자를 무작위로 리턴한다.")
    void rand_test() {
        // when
        String result = rand();

        // then
        assertThat(result.length()).isEqualTo(6);
    }
}
