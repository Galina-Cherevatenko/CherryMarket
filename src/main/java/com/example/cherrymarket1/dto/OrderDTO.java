package com.example.cherrymarket1.dto;



import com.example.cherrymarket1.entities.Status;



public class OrderDTO {


    private int totalPrice;

    private Status status;

    private PersonDTO owner;

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

    public PersonDTO getOwner() {
        return owner;
    }

    public void setOwner(PersonDTO owner) {
        this.owner = owner;
    }
}
