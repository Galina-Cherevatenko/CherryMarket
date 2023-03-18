package com.example.cherrymarket1.repositories;


import com.example.cherrymarket1.models.Delivery;
import com.example.cherrymarket1.models.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    Delivery findByOrder(Order order);
}
