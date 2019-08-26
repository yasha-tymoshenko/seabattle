package com.tymoshenko.seabattle.exception;

public class IllegalTargetException extends RuntimeException {
    public IllegalTargetException(String message) {
        super(message);
    }

    public IllegalTargetException(String message, Throwable cause) {
        super(message, cause);
    }
}
