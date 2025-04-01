package com.api.b_plus_studio.Exceptions;

import com.api.b_plus_studio.dtos.ExceptionResponse;

public class BaseServiceException extends Exception {
    private final String code;
    private final String message;

    BaseServiceException(String code, String message) {
        this(code, message, null);
    }

    private BaseServiceException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.code = code;
    }

    public ExceptionResponse buildErrorResponse() {
        return new ExceptionResponse(this.code, this.message);
    }
}
