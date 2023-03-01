package com.assignment.login.user.api;

import com.assignment.login.global.response.CommonResponse;
import com.assignment.login.user.dto.AuthenticatePhoneDto;
import com.assignment.login.user.dto.ChangePasswordDto;
import com.assignment.login.user.dto.LoginDto;
import com.assignment.login.user.dto.LoginResponseDto;
import com.assignment.login.user.dto.LostPhoneDto;
import com.assignment.login.user.dto.PublishAuthenticatePhoneDto;
import com.assignment.login.user.dto.RegisterDto;
import com.assignment.login.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class LoginApi {
    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        loginService.register(registerDto);
        return CommonResponse.setResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        LoginResponseDto result = loginService.login(loginDto);
        return CommonResponse.setResponse(result);
    }

    @PostMapping("/publish/authenticate/number")
    public ResponseEntity<?> publishAuthenticatePhone(@Valid @RequestBody PublishAuthenticatePhoneDto phoneDto) {
        String number = loginService.publishAuthenticatePhone(phoneDto);
        return CommonResponse.setResponse(number);
    }

    @PostMapping("/publish/lost/password")
    public ResponseEntity<?> publishLostPassword(@Valid @RequestBody LostPhoneDto phoneDto) {
        String number = loginService.publishFindLostPassword(phoneDto);
        return CommonResponse.setResponse(number);
    }

    @PostMapping("/authenticate/number")
    public ResponseEntity<?> authenticatePhone(@Valid @RequestBody AuthenticatePhoneDto phoneDto) {
        String encodePhone = loginService.authenticatePhone(phoneDto);
        return CommonResponse.setResponse(encodePhone);
    }

    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        loginService.changePassword(changePasswordDto);
        return CommonResponse.setResponse();
    }

}
