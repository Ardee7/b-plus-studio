package com.api.b_plus_studio.configurations;

import com.api.b_plus_studio.Exceptions.UserException;
import com.api.b_plus_studio.dtos.ExceptionResponse;
import com.api.b_plus_studio.dtos.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    private static final String EXCEPTION_LOG_MESSAGE = "Encountered an exception: {}; Caused By: {}";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<GenericResponse> handleGenericError(final Exception i) {
        log.error(EXCEPTION_LOG_MESSAGE, i.getMessage(), i.getCause(), i);
        return new ResponseEntity<>(GenericResponse.build(new ExceptionResponse().buildGenericError()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserException.class)
    public final ResponseEntity<GenericResponse> handleUserException(
            final UserException i) {
        log.warn(EXCEPTION_LOG_MESSAGE, i.getMessage(), i.getCause(), i);
        return new ResponseEntity<>(GenericResponse.build(i.buildErrorResponse()), HttpStatus.BAD_REQUEST);
    }


}