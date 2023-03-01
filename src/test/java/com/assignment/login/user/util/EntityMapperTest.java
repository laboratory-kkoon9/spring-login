package com.assignment.login.user.util;

import com.assignment.login.user.domain.User;
import com.assignment.login.user.dto.RegisterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.assignment.login.user.util.EntityMapper.toEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityMapperTest {
    private RegisterDto givenRegisterDto;
    private String givenEmail = "rndrnjs2003@naver.com";
    private String givenPassword = "123123";
    private String givenName = "남궁권";
    private String givenNickname = "남궁원빈";
    private String givenPhone = "01080127226";

    @BeforeEach
    void init() {
        givenRegisterDto = RegisterDto.builder()
                .email(givenEmail)
                .nickname(givenNickname)
                .password(givenPassword)
                .phone(givenPhone)
                .name(givenName)
                .build();
    }

    @Test
    @DisplayName("RegisterDto를 User Entity로 매핑한다.")
    void userMapper_test() {
        // when
        User user = toEntity(givenRegisterDto);

        // then
        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo(givenRegisterDto.getEmail()),
                () -> assertThat(user.getName()).isEqualTo(givenRegisterDto.getName()),
                () -> assertThat(user.getPassword()).isEqualTo(givenRegisterDto.getPassword()),
                () -> assertThat(user.getNickname()).isEqualTo(givenRegisterDto.getNickname()),
                () -> assertThat(user.getPhone()).isEqualTo(givenRegisterDto.getPhone()),
                () -> assertThat(user.getActivated()).isTrue()
        );
    }
}
