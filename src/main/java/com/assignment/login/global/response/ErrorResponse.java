package com.assignment.login.global.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ErrorResponse {
    private int status;
    private String message;

    @Builder
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .status(status.value())
                        .message(message)
                        .build());
    }
}
