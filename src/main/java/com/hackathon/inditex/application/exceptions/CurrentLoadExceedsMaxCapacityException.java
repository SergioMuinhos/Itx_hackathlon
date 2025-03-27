package com.hackathon.inditex.application.exceptions;

/**
 * Center Not Found Exception.
 */
public class CurrentLoadExceedsMaxCapacityException extends RuntimeException {
    public CurrentLoadExceedsMaxCapacityException(String message) {
        super(message);
    }
}
