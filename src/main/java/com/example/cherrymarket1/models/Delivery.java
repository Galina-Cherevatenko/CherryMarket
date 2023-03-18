package com.example.cherrymarket1.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Delivery")
public class Delivery {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="order_id", referencedColumnName = "id")
    private Order order;

    @Column(name= "start_time")
    private LocalDateTime start_time;

    @Column(name= "finish_time")
    private LocalDateTime finish_time;

    public Delivery() {
    }

    public Delivery(Order order, LocalDateTime start_time, LocalDateTime finish_time) {
        this.order = order;
        this.start_time = start_time;
        this.finish_time = finish_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
