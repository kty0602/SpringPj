package com.sparta.springprepare.exception;

public class AlreadyDeleteException extends RuntimeException {
    public AlreadyDeleteException() {
        super("이미 삭제된 일정입니다.");
    }
}
