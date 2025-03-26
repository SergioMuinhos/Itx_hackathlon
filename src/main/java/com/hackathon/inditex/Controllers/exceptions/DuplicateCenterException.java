package com.hackathon.inditex.Controllers.exceptions;

public class DuplicateCenterException extends RuntimeException {
    public DuplicateCenterException(String message) {
        super(message);
    }
}
