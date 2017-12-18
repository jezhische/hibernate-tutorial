package com.jezh.jdbc.golovach.exceptions;

public class NotUniqueEmailException extends DBException {
    public NotUniqueEmailException(String message) {
        super(message);
    }

    public NotUniqueEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
