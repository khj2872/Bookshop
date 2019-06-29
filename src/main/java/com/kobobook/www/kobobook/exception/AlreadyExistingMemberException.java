package com.kobobook.www.kobobook.exception;

public class AlreadyExistingMemberException extends RuntimeException {
    public AlreadyExistingMemberException() { }

    public AlreadyExistingMemberException(String message) {
        super(message);
    }
}
