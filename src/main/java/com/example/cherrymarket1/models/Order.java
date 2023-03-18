package com.example.cherrymarket1.models;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Ordered_items")
public class Order {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "order")
    private List<ItemInOrder> itemsInOrder;

    @Column(name= "total_price")
    private int totalPrice;

    @Column(name= "status")
    private Status status;

    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST)
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name="person_id",referencedColumnName = "id")
    private Person owner;

    public Order() {
    }

    public Order(int id, List<ItemInOrder> itemsInOrder, int totalPrice, Status status, Person owner) {
        this.id = id;
        this.itemsInOrder = itemsInOrder;
        this.totalPrice = totalPrice;
        this.status = status;
        this.owner = owner;
    }

    public Order(List<ItemInOrder> itemsInOrder, int totalPrice, Status status, Person owner) {
        this.itemsInOrder = itemsInOrder;
        this.totalPrice = totalPrice;
        this.status = status;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemInOrder> getItemsInOrder() {
        return itemsInOrder;
    }

    public void setItemsInOrder(List<ItemInOrder> itemsInOrder) {
        this.itemsInOrder = itemsInOrder;
    }
    public void addItemsInOrder (ItemInOrder itemInOrder){
        if(this.itemsInOrder==null)
            this.itemsInOrder = new ArrayList<>();
        this.itemsInOrder.add(itemInOrder);
        itemInOrder.setOrder(this);
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && totalPrice == order.totalPrice && Objects.equals(itemsInOrder, order.itemsInOrder) && status == order.status && Objects.equals(owner, order.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemsInOrder, totalPrice, status, owner);
    }
}
