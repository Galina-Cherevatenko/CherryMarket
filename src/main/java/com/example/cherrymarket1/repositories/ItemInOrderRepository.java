package com.example.cherrymarket1.repositories;

import com.example.cherrymarket1.entities.ItemInOrder;
import com.example.cherrymarket1.entities.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemInOrderRepository extends JpaRepository<ItemInOrder, Integer> {
    List<ItemInOrder> findByOrder(Order order);
}
