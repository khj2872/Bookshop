package com.kobobook.www.kobobook.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("로그인하지 않은 사용자입니다.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
