package com.example.cherrymarket1.entities;

import javax.persistence.*;

@Entity
@Table(name = "Item_in_order")
public class ItemInOrder {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name="order_id", referencedColumnName = "id")
    private Order order;

    @Column(name= "quantity")
    private int quantity;

    public ItemInOrder() {
    }

    public ItemInOrder(Item item, Order order, int quantity) {
        this.item = item;
        this.order = order;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
