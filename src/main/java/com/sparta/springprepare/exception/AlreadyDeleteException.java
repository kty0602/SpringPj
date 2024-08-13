package com.sparta.springprepare.exception;

public class AlreadyDeleteException extends RuntimeException {
    public AlreadyDeleteException(String message) {
        super(message);
    }
}
