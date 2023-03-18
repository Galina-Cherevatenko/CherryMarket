package com.example.cherrymarket1.repositories;

import com.example.cherrymarket1.models.Order;
import com.example.cherrymarket1.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByOwner(Person person);

}
