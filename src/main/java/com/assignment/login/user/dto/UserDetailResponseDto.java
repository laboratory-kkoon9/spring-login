package com.assignment.login.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailResponseDto {
    private String email;
    private String name;
    private String nickname;

    @Builder
    public UserDetailResponseDto(String email, String name, String nickname) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
    }
}
