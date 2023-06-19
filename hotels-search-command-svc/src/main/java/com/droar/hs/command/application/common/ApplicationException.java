package com.droar.hs.command.application.common;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private final HttpStatus httpStatusCode;

    public ApplicationException(final HttpStatus exceptionCode, final String errorMessage, final Throwable err) {
        super(errorMessage, err);
        this.httpStatusCode = exceptionCode;
    }

    public ApplicationException(final HttpStatus exceptionCode, final String errorMessage) {
        super(errorMessage);
        this.httpStatusCode = exceptionCode;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}