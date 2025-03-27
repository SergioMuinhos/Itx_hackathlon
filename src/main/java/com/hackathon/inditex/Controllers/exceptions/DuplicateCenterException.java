package com.hackathon.inditex.Controllers.exceptions;

/**
 * Center Not Found Exception.
 */
public class DuplicateCenterException extends RuntimeException {
    public DuplicateCenterException(String message) {
        super(message);
    }
}
