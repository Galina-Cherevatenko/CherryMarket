package com.example.cherrymarket1.util;

public class OrderErrorResponse {
    private String message;

    public OrderErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
