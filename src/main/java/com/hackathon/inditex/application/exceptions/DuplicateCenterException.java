package com.hackathon.inditex.application.exceptions;

/**
 * Center Not Found Exception.
 */
public class DuplicateCenterException extends RuntimeException {
    public DuplicateCenterException(String message) {
        super(message);
    }
}
