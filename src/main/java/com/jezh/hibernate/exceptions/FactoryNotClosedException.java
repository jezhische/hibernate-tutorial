package com.jezh.hibernate.exceptions;

public class FactoryNotClosedException extends Exception {
    public FactoryNotClosedException(String message) {
        super(message);
    }
    public FactoryNotClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
