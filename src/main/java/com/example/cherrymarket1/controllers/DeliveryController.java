package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.dto.OrderDTO;

import com.example.cherrymarket1.entities.Status;
import com.example.cherrymarket1.mappers.OrderMapper;
import com.example.cherrymarket1.services.DeliveryService;
import com.example.cherrymarket1.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(OrderService orderService, OrderMapper orderMapper, DeliveryService deliveryService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.deliveryService = deliveryService;
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ROLE_COURIER') or hasRole('ROLE_ADMIN')")
    public List<OrderDTO> getOrdersByStatusCreated(){
        return orderService.findAll().stream().filter(order -> order.getStatus().equals(Status.CREATED))
                .map(orderMapper::convertToOrderDTO).collect(Collectors.toList());
    }

    @PutMapping("/{id}/start")
    @PreAuthorize("hasRole('ROLE_COURIER')")
    public ResponseEntity<HttpStatus> takeOrderToDelivery(@PathVariable("id") int order_id) {
        if(deliveryService.startOrderDelivery(order_id))
            return ResponseEntity.ok(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}/finish")
    @PreAuthorize("hasRole('ROLE_COURIER')")
    public ResponseEntity<HttpStatus> finishOrderDelivery(@PathVariable("id") int order_id) {
        if(deliveryService.finishOrderDelivery(order_id))
            return ResponseEntity.ok(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
