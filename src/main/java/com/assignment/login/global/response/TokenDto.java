package com.assignment.login.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {
    private Long userId;
    private String email;

    @Builder
    public TokenDto(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
