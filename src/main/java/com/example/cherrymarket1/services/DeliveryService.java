package com.example.cherrymarket1.services;

import com.example.cherrymarket1.entities.Delivery;
import com.example.cherrymarket1.entities.Order;
import com.example.cherrymarket1.entities.Status;
import com.example.cherrymarket1.repositories.DeliveryRepository;
import com.example.cherrymarket1.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, OrderService orderService, OrderRepository orderRepository) {
        this.deliveryRepository = deliveryRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public boolean startOrderDelivery (int order_id){
        Order order = orderService.findOne(order_id);
        if (order.getStatus()==Status.CREATED){
            order.setStatus(Status.INDELIVERY);
            orderRepository.save(order);
            Delivery delivery = new Delivery();
            delivery.setOrder(order);
            delivery.setStart_time(LocalDateTime.now());
            deliveryRepository.save(delivery);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean finishOrderDelivery (int order_id){
        Order order = orderService.findOne(order_id);
        if (order.getStatus()==Status.INDELIVERY){
            order.setStatus(Status.DELIVERED);
            orderRepository.save(order);
            Delivery delivery = deliveryRepository.findByOrder(order);
            delivery.setFinish_time(LocalDateTime.now());
            deliveryRepository.save(delivery);
            return true;
        }
        return false;
    }
}
