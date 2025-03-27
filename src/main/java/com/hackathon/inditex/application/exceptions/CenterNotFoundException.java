package com.hackathon.inditex.application.exceptions;

/**
 * Center Not Found Exception.
 */
public class CenterNotFoundException extends RuntimeException {
    public CenterNotFoundException(String message) {
        super(message);
    }
}
