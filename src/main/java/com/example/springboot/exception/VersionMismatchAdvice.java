package com.example.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class VersionMismatchAdvice {

    @ExceptionHandler(VersionMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String versionMismatchHandler(VersionMismatchException ex) {
        return ex.getMessage();
    }
}
