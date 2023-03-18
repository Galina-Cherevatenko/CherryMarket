package com.example.cherrymarket1.repositories;

import com.example.cherrymarket1.models.ItemInOrder;
import com.example.cherrymarket1.models.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemInOrderRepository extends JpaRepository<ItemInOrder, Integer> {
    List<ItemInOrder> findByOrder(Order order);
}
