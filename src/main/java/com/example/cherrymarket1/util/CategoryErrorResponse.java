package com.example.cherrymarket1.util;

public class CategoryErrorResponse {
    private String message;

    public CategoryErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
