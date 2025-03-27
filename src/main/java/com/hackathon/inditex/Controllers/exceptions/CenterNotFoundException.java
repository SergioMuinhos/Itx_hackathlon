package com.hackathon.inditex.Controllers.exceptions;

/**
 * Center Not Found Exception.
 */
public class CenterNotFoundException extends RuntimeException {
    public CenterNotFoundException(String message) {
        super(message);
    }
}
