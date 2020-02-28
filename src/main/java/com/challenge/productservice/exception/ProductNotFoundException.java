package com.challenge.productservice.exception;

public class ProductNotFoundException extends RuntimeException {

    private String message;

    public ProductNotFoundException(String message) {
        super(message);
    }
}
