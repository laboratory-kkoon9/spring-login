package com.assignment.login.user.service;

import com.assignment.login.global.config.JwtTokenManager;
import com.assignment.login.global.response.TokenDto;
import com.assignment.login.infra.redis.RedisService;
import com.assignment.login.user.domain.User;
import com.assignment.login.user.dto.AuthenticatePhoneDto;
import com.assignment.login.user.dto.ChangePasswordDto;
import com.assignment.login.user.dto.LoginDto;
import com.assignment.login.user.dto.LoginResponseDto;
import com.assignment.login.user.dto.LostPhoneDto;
import com.assignment.login.user.dto.PublishAuthenticatePhoneDto;
import com.assignment.login.user.dto.RegisterDto;
import com.assignment.login.user.repository.UserRepository;
import com.assignment.login.user.util.CryptUtil;
import com.assignment.login.user.util.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class LoginService {
    private static final String DUPLICATE_EMAIL_MESSAGE = "중복된 이메일이 존재합니다.";
    private static final String ALREADY_USED_PHONE_MESSAGE = "이미 사용 중인 핸드폰 번호입니다.";
    private static final String INVALID_AUTHENTICATE_NUMBER_MESSAGE = "인증 번호가 불일치합니다.";
    private static final String INVALID_PHONE_NUMBER_MESSAGE = "인증된 휴대폰 번호가 아닙니다.";
    private static final String NOT_EXIST_EMAIL_MESSAGE = "존재하지 않은 이메일입니다.";
    private static final String INVALID_PASSWORD_MESSAGE = "비밀번호가 일치하지 않습니다.";
    private static final String INCONSISTENCY_PHONE_NUMBER_MESSAGE = "휴대폰 번호가 일치하지 않습니다.";
    private final UserRepository userRepository;
    private final PhoneTextService phoneTextService;
    private final RedisService redisService;
    private final JwtTokenManager jwtTokenManager;

    @Transactional
    public void register(final RegisterDto registerDto) {
        final String email = registerDto.getEmail();
        final String phone = registerDto.getPhone();
        final String encodePhone = registerDto.getEncodePhone();
        validateEmail(email);

        if (!CryptUtil.matches(phone, encodePhone)) {
            throw new IllegalArgumentException(INVALID_PHONE_NUMBER_MESSAGE);
        }

        userRepository.save(EntityMapper.toEntity(registerDto));
    }

    private void validateEmail(final String email) {
        boolean duplicated = userRepository.existsByEmailAndActivated(email, true);
        if (duplicated) {
            throw new IllegalArgumentException(DUPLICATE_EMAIL_MESSAGE);
        }
    }

    @Transactional(readOnly = true)
    public String publishAuthenticatePhone(final PublishAuthenticatePhoneDto publishAuthenticatePhoneDto) {
        final String phone = publishAuthenticatePhoneDto.getPhone();
        final boolean existed = userRepository.existsByPhoneAndActivated(phone, true);
        if (existed) {
            throw new IllegalArgumentException(ALREADY_USED_PHONE_MESSAGE);
        }

        final String sendAuthenticateNumber = this.phoneTextService.sendAuthenticateNumber(phone);

        redisService.set(phone, sendAuthenticateNumber);
        return sendAuthenticateNumber;
    }

    @Transactional(readOnly = true)
    public String authenticatePhone(final AuthenticatePhoneDto authenticatePhoneDto) {
        final String phone = authenticatePhoneDto.getPhone();
        final String authenticateNumber = authenticatePhoneDto.getAuthenticateNumber();
        if (!redisService.get(phone).equals(authenticateNumber)) {
            throw new IllegalArgumentException(INVALID_AUTHENTICATE_NUMBER_MESSAGE);
        }

        redisService.delete(phone);
        return CryptUtil.encode(phone);
    }

    @Transactional
    public LoginResponseDto login(final LoginDto loginDto) {
        final String email = loginDto.getEmail();
        final String password = loginDto.getPassword();

        User user = userRepository.findByEmailAndActivated(email, true)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_EMAIL_MESSAGE));

        if (!CryptUtil.matches(password, user.getPassword())) {
            throw new IllegalArgumentException(INVALID_PASSWORD_MESSAGE);
        }

        final TokenDto tokenDto = TokenDto.builder()
                .email(user.getEmail())
                .userId(user.getId())
                .build();

        final String accessToken = this.jwtTokenManager.generateToken(tokenDto);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .build();
    }

    @Transactional(readOnly = true)
    public String publishFindLostPassword(final LostPhoneDto lostPhoneDto) {
        final String phone = lostPhoneDto.getPhone();
        final String email = lostPhoneDto.getEmail();

        User user = userRepository.findByEmailAndActivated(email, true)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_EMAIL_MESSAGE));

        if(!CryptUtil.matches(phone, user.getPhone())) {
            throw new IllegalArgumentException(INCONSISTENCY_PHONE_NUMBER_MESSAGE);
        }

        final String sendAuthenticateNumber = this.phoneTextService.sendAuthenticateNumber(phone);

        redisService.set(phone, sendAuthenticateNumber);
        return sendAuthenticateNumber;
    }

    @Transactional
    public void changePassword(final ChangePasswordDto changePasswordDto) {
        final String email = changePasswordDto.getEmail();
        final String phone = changePasswordDto.getPhone();
        final String encodePhone = changePasswordDto.getEncodePhone();

        if (!CryptUtil.matches(phone, encodePhone)) {
            throw new IllegalArgumentException(INVALID_PHONE_NUMBER_MESSAGE);
        }

        User user = userRepository.findByEmailAndActivated(email, true)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_EMAIL_MESSAGE));
        user.changePassword(changePasswordDto.getNewPassword());
    }
}
