package com.assignment.login.user.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.assignment.login.user.util.CryptUtil.encode;
import static com.assignment.login.user.util.CryptUtil.matches;
import static org.assertj.core.api.Assertions.assertThat;

public class CryptUtilTest {
    private String givenPassword;
    private String givenEncodePassword;

    @BeforeEach
    public void init() {
        givenPassword = "123123";
        givenEncodePassword = encode(givenPassword);
    }

    @DisplayName("같은 비밀번호를 암호화하더라도 서로 다른 암호화된 비밀번호가 리턴된다.")
    @Test
    void encode_test() {
        // when
        String result = encode(givenPassword);

        // then
        assertThat(result).isNotEqualTo(givenEncodePassword);
    }

    @DisplayName("복호화 성공 테스트")
    @Test
    void matches_success_test() {
        // when
        boolean result = matches(givenPassword, givenEncodePassword);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("복호화 실패 테스트")
    @Test
    void matches_fail_test() {
        // given
        String wrongPassword = "wrongPassword";

        // when
        boolean result = matches(wrongPassword, givenEncodePassword);

        // then
        assertThat(result).isFalse();
    }
}
