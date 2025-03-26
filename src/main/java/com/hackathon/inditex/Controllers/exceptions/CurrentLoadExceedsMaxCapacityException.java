package com.hackathon.inditex.Controllers.exceptions;

public class CurrentLoadExceedsMaxCapacityException extends RuntimeException {
    public CurrentLoadExceedsMaxCapacityException(String message) {
        super(message);
    }
}
