package com.hackathon.inditex.Controllers.exceptions;

public class CenterNotFoundException extends RuntimeException {
    public CenterNotFoundException(String message) {
        super(message);
    }
}
