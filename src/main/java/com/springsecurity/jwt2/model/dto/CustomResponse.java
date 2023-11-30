package com.springsecurity.jwt2.model.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CustomResponse {
    private String message;
    private boolean success;
    private Object data;

    public static CustomResponse ok(Object data) {
        return CustomResponse.builder()
                .success(true)
                .message("OK")
                .data(data)
                .build();
    }

    public static CustomResponse ok() {
        return CustomResponse.builder()
                .success(true)
                .message("OK")
                .build();
    }

    public static CustomResponse error(String message) {
        return CustomResponse.builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}
