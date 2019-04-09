package com.binance.dex.service.rest;

public class DexServiceException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public DexServiceException(String message) {
        super(message);
    }
    
    public DexServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
