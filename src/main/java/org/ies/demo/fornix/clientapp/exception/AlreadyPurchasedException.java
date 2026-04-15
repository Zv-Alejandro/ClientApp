package org.ies.demo.fornix.clientapp.exception;

public class AlreadyPurchasedException extends RuntimeException {
    public AlreadyPurchasedException(String message) {
        super(message);
    }
}