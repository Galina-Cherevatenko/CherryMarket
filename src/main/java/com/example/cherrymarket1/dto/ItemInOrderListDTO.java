package com.example.cherrymarket1.dto;

import java.util.List;

public class ItemInOrderListDTO {
    private List<ItemInOrderDTO> items;

    public ItemInOrderListDTO(List<ItemInOrderDTO> items) {
        this.items = items;
    }

    public List<ItemInOrderDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemInOrderDTO> items) {
        this.items = items;
    }
}
