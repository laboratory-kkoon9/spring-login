package com.assignment.login.user.service;

import com.assignment.login.global.config.JwtTokenManager;
import com.assignment.login.infra.redis.RedisService;
import com.assignment.login.user.dto.PublishAuthenticatePhoneDto;
import com.assignment.login.user.dto.RegisterDto;
import com.assignment.login.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static com.assignment.login.user.util.CryptUtil.encode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PhoneTextService phoneTextService;
    @Autowired
    private LoginService loginService;

    private JwtTokenManager jwtTokenManager;
    private RedisService redisService = new RedisService();


    private RegisterDto givenRegisterDto;
    private String givenEmail = "rndrnjs2003@naver.com";
    private String givenPassword = "123123";
    private String givenName = "남궁권";
    private String givenNickname = "남궁원빈";
    private String givenPhone = "01080127226";

    private PublishAuthenticatePhoneDto phoneDto = new PublishAuthenticatePhoneDto(givenPhone);

    @BeforeEach
    void init() {
        loginService = new LoginService(userRepository, phoneTextService, redisService, jwtTokenManager);
        givenRegisterDto = RegisterDto.builder()
                .email(givenEmail)
                .nickname(givenNickname)
                .password(givenPassword)
                .phone(givenPhone)
                .encodePhone(encode(givenPhone))
                .name(givenName)
                .build();
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 회원가입을 시도한다면, IllegalArgumentException을 던진다.")
    void register_test_1() {
        // given
        given(userRepository.existsByEmailAndActivated(any(), any())).willReturn(true);

        // then
        assertThatThrownBy(() -> loginService.register(givenRegisterDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복된 이메일이 존재합니다.");
    }

    @Test
    @DisplayName("인증된 번호가 아니라면 IllegalArgumentException을 던진다.")
    void register_test_2() {
        // given
        RegisterDto notAuthenticateRegisterDto = RegisterDto.builder()
                .email(givenEmail)
                .nickname(givenNickname)
                .password(givenPassword)
                .phone(givenPhone)
                .encodePhone(givenPhone)
                .name(givenName)
                .build();
        given(userRepository.existsByEmailAndActivated(any(), any())).willReturn(false);


        // then
        assertThatThrownBy(() -> loginService.register(notAuthenticateRegisterDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("인증된 휴대폰 번호가 아닙니다.");
    }

    @Test
    @DisplayName("회원가입에 성공하면 아무런 에러가 발생하지 않는다.")
    void register_test_3() {
        // given
        given(userRepository.existsByEmailAndActivated(any(), any())).willReturn(false);

        // then
        assertDoesNotThrow(
                () -> loginService.register(givenRegisterDto)
        );
    }

    @Test
    @DisplayName("이미 존재하는 핸드폰 번호로 인증을 시도한다면, IllegalArgumentException을 던진다.")
    void publishAuthenticatePhone_test_1() {
        // given
        given(userRepository.existsByPhoneAndActivated(any(), any())).willReturn(true);

        // then
        assertThatThrownBy(() -> loginService.publishAuthenticatePhone(phoneDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 사용 중인 핸드폰 번호입니다.");
    }

    @Test
    @DisplayName("존재하지 않은 핸드폰 번호로 인증을 시도한다면, 인증번호를 리턴한다.")
    void publishAuthenticatePhone_test_2() {
        // given
        given(userRepository.existsByPhoneAndActivated(any(), any())).willReturn(false);
        given(phoneTextService.sendAuthenticateNumber(any())).willReturn("123456");

        // when
        loginService.publishAuthenticatePhone(phoneDto);
        String number = redisService.get(givenPhone);
        String notExisted = redisService.get("010-0000-0000");

        // then
        assertAll(
                () -> assertThat(number.length()).isEqualTo(6),
                () -> assertThat(notExisted).isNull()
        );
    }
}
