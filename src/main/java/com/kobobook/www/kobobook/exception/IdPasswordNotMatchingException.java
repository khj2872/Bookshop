package com.kobobook.www.kobobook.exception;

public class IdPasswordNotMatchingException extends RuntimeException {
    public IdPasswordNotMatchingException() {
    }

    public IdPasswordNotMatchingException(String message) {
        super(message);
    }
}
