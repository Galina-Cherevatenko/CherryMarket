package com.example.cherrymarket1.repositories;


import com.example.cherrymarket1.entities.Delivery;
import com.example.cherrymarket1.entities.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    Delivery findByOrder(Order order);
}
