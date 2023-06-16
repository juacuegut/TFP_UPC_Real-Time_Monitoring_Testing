package com.orangehrm.exceptions;

public class InputOutputException extends FrameworkException {

    public InputOutputException(String message) {
        super(message);
    }

    public InputOutputException(String message, Throwable cause) {
        super(message, cause);
    }
}
