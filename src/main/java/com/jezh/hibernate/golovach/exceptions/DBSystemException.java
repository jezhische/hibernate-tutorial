package com.jezh.hibernate.golovach.exceptions;

public class DBSystemException extends DBException {
    public DBSystemException(String message) {
        super(message);
    }

    public DBSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
