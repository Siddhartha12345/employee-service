package com.employee.exception;

public class GenericException extends RuntimeException {

    public GenericException() {
        super("Generic Error occurred!");
    }

    public GenericException(String message) {
        super(message);
    }
}
