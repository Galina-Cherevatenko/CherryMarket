package com.example.cherrymarket1.services;

import com.example.cherrymarket1.entities.ItemInOrder;
import com.example.cherrymarket1.repositories.ItemInOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class ItemInOrderService {
    private final ItemInOrderRepository itemInOrderRepository;
    private final OrderService orderService;

    @Autowired
    public ItemInOrderService(ItemInOrderRepository itemInOrderRepository, OrderService orderService) {
        this.itemInOrderRepository = itemInOrderRepository;
        this.orderService = orderService;
    }

    public List<ItemInOrder> findAllByOrderId (int order_id){
        List<ItemInOrder> itemsInOrder = itemInOrderRepository.findByOrder(orderService.findOne(order_id));
        return itemsInOrder;
    }
}
