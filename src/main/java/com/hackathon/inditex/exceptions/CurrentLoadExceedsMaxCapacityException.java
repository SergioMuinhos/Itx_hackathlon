package com.hackathon.inditex.exceptions;

public class CurrentLoadExceedsMaxCapacityException extends RuntimeException {
    public CurrentLoadExceedsMaxCapacityException(String message) {
        super(message);
    }
}
