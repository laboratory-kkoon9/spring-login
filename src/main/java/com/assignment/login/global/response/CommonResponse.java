package com.assignment.login.global.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
public class CommonResponse<T> {

    private boolean success;
    private String message;
    private T response;

    public CommonResponse(T response) {
        this.success = true;
        this.message = "success";
        this.response = response;
    }

    @Builder
    public CommonResponse(Boolean success, String message, T response) {
        this.success = success;
        this.message = message;
        this.response = response;
    }

    public static ResponseEntity<?> setResponse(Object data) {
        return ResponseEntity.ok()
                .body(data);
    }
    public static ResponseEntity<?> setResponse() {
        return ResponseEntity.ok()
                .body("ok");
    }
}
