package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.dto.OrderDTO;

import com.example.cherrymarket1.models.Order;
import com.example.cherrymarket1.models.Status;
import com.example.cherrymarket1.services.DeliveryService;
import com.example.cherrymarket1.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(OrderService orderService, ModelMapper modelMapper, DeliveryService deliveryService) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.deliveryService = deliveryService;
    }

    @GetMapping("/orders")
    public List<OrderDTO> getOrdersByStatusCreated(){
        return orderService.findAll().stream().filter(order -> order.getStatus().equals(Status.CREATED))
                .map(this::convertToOrderDTO).collect(Collectors.toList());
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<HttpStatus> takeOrderToDelivery(@PathVariable("id") int order_id) {
        if(deliveryService.startOrderDelivery(order_id))
            return ResponseEntity.ok(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<HttpStatus> finishOrderDelivery(@PathVariable("id") int order_id) {
        if(deliveryService.finishOrderDelivery(order_id))
            return ResponseEntity.ok(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    private OrderDTO convertToOrderDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }
}
