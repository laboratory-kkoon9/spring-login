package com.assignment.login.user.api;

import com.assignment.login.global.response.CommonResponse;
import com.assignment.login.user.dto.UserDetailResponseDto;
import com.assignment.login.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserApi {
    private final UserService userService;

    @GetMapping("/my-info")
    public ResponseEntity<?> userInfo() {
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        UserDetailResponseDto result = userService.userInfo(userId);
        return CommonResponse.setResponse(result);
    }
}
