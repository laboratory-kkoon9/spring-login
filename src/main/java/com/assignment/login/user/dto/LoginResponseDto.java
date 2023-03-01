package com.assignment.login.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final String accessToken;
    private final Long userId;

    @Builder
    public LoginResponseDto(String accessToken, Long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }
}
