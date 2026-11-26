package com.induztek.backend.exception;

    
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) { super(message); }
}