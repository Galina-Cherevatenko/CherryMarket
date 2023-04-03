package com.example.cherrymarket1.entities;



import javax.persistence.*;

import javax.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "Item")
public class Item {

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "item")
    private List<ItemInOrder> itemsInOrder;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price")
    private int price;

    @Column(name = "item_quantity")
    private int itemQuantity;
    @ManyToOne
    @JoinColumn(name="category_id", referencedColumnName = "id")
    private Category category;

    public Item() {
    }

    public Item(String itemName, int price, int itemQuantity, Category category) {
        this.itemName = itemName;
        this.price = price;
        this.itemQuantity = itemQuantity;
        this.category = category;
    }

    public Item(int id, String itemName, int price, int itemQuantity, Category category) {
        this.id=id;
        this.itemName = itemName;
        this.price = price;
        this.itemQuantity = itemQuantity;
        this.category = category;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}