package com.example.cherrymarket1.util;

public enum CustomStatus {

        SUCCESS("Item was added to order."),
        NOT_FOUND("Not found"),
        EXCEPTION("Item was not added!");

        private final String message;

        CustomStatus(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }


