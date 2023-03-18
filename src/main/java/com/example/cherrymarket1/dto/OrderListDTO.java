package com.example.cherrymarket1.dto;

import java.util.List;

public class OrderListDTO {
    private List<OrderDTO> orders;

    public OrderListDTO(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
