package com.example.cherrymarket1.util;

public class ItemErrorResponse {
    private String message;

    public ItemErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
