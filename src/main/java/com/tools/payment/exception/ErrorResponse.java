package com.tools.payment.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private String message;
    private int status;
    private Long timestamp;

    public ErrorResponse(String message, int status, Long timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }
}
