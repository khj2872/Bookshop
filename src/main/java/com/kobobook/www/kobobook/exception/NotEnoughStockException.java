package com.kobobook.www.kobobook.exception;

@SuppressWarnings("serial")
public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() { }

    public NotEnoughStockException(String message) {
        super(message);
    }

}