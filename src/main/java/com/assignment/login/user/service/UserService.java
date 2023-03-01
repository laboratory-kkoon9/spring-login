package com.assignment.login.user.service;

import com.assignment.login.user.domain.User;
import com.assignment.login.user.dto.UserDetailResponseDto;
import com.assignment.login.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private static final String NOT_EXIST_USER_MESSAGE = "Id에 해당하는 유저가 없습니다.";
    private final UserRepository userRepository;

    public UserDetailResponseDto userInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_USER_MESSAGE));

        return UserDetailResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .build();
    }
}
