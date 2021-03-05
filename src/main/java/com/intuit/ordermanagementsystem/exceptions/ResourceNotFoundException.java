package com.intuit.ordermanagementsystem.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    String message;

    public ResourceNotFoundException(String s) {
        this.message = s;
    }

}
