package com.employee.exception;

import lombok.Getter;

@Getter
public class GenericException extends RuntimeException {

    private String errorCode;

    public GenericException() {
        super("Generic Error occurred!");
    }

    public GenericException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
