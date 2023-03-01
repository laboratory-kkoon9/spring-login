package com.assignment.login.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "[이메일]은 필수값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "[비밀번호]는 필수값입니다.")
    private String password;

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
