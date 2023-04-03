package com.example.cherrymarket1.dto;

import com.example.cherrymarket1.entities.Item;


public class ItemInOrderDTO {

    private Item item;

    private int quantity;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
