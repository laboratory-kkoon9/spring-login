package com.assignment.login.user.dto;

import com.assignment.login.user.util.UserConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class LostPhoneDto {
    @NotBlank(message = "[이메일]은 필수값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "[핸드폰번호]는 필수값입니다.")
    @Pattern(regexp = UserConstants.MOBILE_NUMBER_PATTERN, message = "올바른 전화번호 형식이 아닙니다. 010-1234-1234와 같은 형식으로 입력해 주세요.")
    private String phone;

    public LostPhoneDto(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }
}
