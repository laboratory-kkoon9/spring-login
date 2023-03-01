package com.assignment.login.user.repository;

import com.assignment.login.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private static final String existedEmail = "rndrnjs2003@naver.com";
    private static final String notExistedEmail = "rndrnjs2003@ably.com";

    private static final String existedPhone = "01080127226";
    private static final String notExistedPhone = "010000000000";

    @ParameterizedTest
    @MethodSource("duplicateEmailTest")
    @DisplayName("이메일과 활성화 여부에 따른 예상값을 리턴한다.")
    void existsByEmailAndActivate_test1(String email, Boolean activated, Boolean expected) {
        // given
        Boolean givenActivated = true;
        User user = User.builder()
                .email(existedEmail)
                .activated(givenActivated)
                .build();
        userRepository.save(user);

        // when
        boolean result = userRepository.existsByEmailAndActivated(email, activated);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> duplicateEmailTest() {
        return Stream.of(
                Arguments.of(existedEmail, true, true),
                Arguments.of(existedEmail, false, false),
                Arguments.of(notExistedEmail, true, false),
                Arguments.of(notExistedEmail, false, false)
        );
    }

    @ParameterizedTest
    @MethodSource("duplicatePhoneTest")
    @DisplayName("핸드폰 번호와 활성화 여부에 따른 예상값을 리턴한다.")
    void existsByPhoneAndActivate_test1(String phone, Boolean activated, Boolean expected) {
        // given
        Boolean givenActivated = true;
        User user = User.builder()
                .phone(existedPhone)
                .activated(givenActivated)
                .build();
        userRepository.save(user);

        // when
        boolean result = userRepository.existsByPhoneAndActivated(phone, activated);

        // then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> duplicatePhoneTest() {
        return Stream.of(
                Arguments.of(existedPhone, true, true),
                Arguments.of(existedPhone, false, false),
                Arguments.of(notExistedPhone, true, false),
                Arguments.of(notExistedPhone, false, false)
        );
    }
}
