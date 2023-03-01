package com.assignment.login.user.util;

import com.assignment.login.user.domain.User;
import com.assignment.login.user.dto.RegisterDto;

import static com.assignment.login.user.util.CryptUtil.encode;

public class EntityMapper {
    private EntityMapper() {}

    public static User toEntity(RegisterDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(encode(dto.getPassword()))
                .name(dto.getName())
                .phone(encode(dto.getPhone()))
                .activated(true)
                .build();
    }
}
