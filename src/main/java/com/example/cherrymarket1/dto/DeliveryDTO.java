package com.example.cherrymarket1.dto;

import com.example.cherrymarket1.entities.Order;


import java.time.LocalDateTime;

public class DeliveryDTO {
    private Order order;

    private LocalDateTime start_time;

    private LocalDateTime finish_time;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(LocalDateTime finish_time) {
        this.finish_time = finish_time;
    }
}
