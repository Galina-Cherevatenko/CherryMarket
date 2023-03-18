package com.example.cherrymarket1.dto;


import javax.validation.constraints.NotEmpty;
;

public class ItemDTO {

    @NotEmpty(message = "Name should not be empty")
    private String itemName;

    private int price;

    private int itemQuantity;

    private CategoryDTO category;

    public ItemDTO() {
    }

    public ItemDTO(String itemName, int price, int itemQuantity, CategoryDTO category) {
        this.itemName = itemName;
        this.price = price;
        this.itemQuantity = itemQuantity;
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}
