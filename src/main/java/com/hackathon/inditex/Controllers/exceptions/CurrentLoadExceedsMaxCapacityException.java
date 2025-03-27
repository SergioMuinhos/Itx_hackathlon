package com.hackathon.inditex.Controllers.exceptions;

/**
 * Center Not Found Exception.
 */
public class CurrentLoadExceedsMaxCapacityException extends RuntimeException {
    public CurrentLoadExceedsMaxCapacityException(String message) {
        super(message);
    }
}
