package com.example.cherrymarket1.entities;

public enum Status {
    BASKET("корзина"),
    CREATED("создан"),
    INDELIVERY("в доставке"),
    DELIVERED("доставлен");

    private final String statusName;
    Status(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
