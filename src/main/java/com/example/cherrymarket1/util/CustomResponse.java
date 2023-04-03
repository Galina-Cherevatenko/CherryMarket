package com.example.cherrymarket1.util;

import java.util.Collection;

public class CustomResponse {

    private String message;

    public CustomResponse(CustomStatus customStatus) {
        this.message = customStatus.getMessage();

        }

    public String getMessage() {
            return message;
        }

    public void setMessage(String message) {
            this.message = message;
        }

}
