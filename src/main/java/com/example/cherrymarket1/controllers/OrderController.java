package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.dto.*;

import com.example.cherrymarket1.mappers.ItemInOrderMapper;

import com.example.cherrymarket1.mappers.OrderMapper;
import com.example.cherrymarket1.services.ItemInOrderService;
import com.example.cherrymarket1.services.OrderService;
import com.example.cherrymarket1.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final ItemInOrderService itemInOrderService;
    private final OrderMapper orderMapper;
    private final ItemInOrderMapper itemInOrderMapper;
    @Autowired
    public OrderController(OrderService orderService, ItemInOrderService itemInOrderService,
                           OrderMapper orderMapper, ItemInOrderMapper itemInOrderMapper) {
        this.orderService = orderService;
        this.itemInOrderService = itemInOrderService;
        this.orderMapper = orderMapper;
        this.itemInOrderMapper = itemInOrderMapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public OrderDTO getOrder(@PathVariable("id") int id){
        return orderMapper.convertToOrderDTO(orderService.findOne(id));

    }

    @GetMapping("/owner/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<OrderDTO> getOrdersByOwnerId(@PathVariable("id") int id){
        return orderService.findAllByOwnerId(id).stream()
                .map(orderMapper::convertToOrderDTO).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public CustomResponse addItemToOrder(@PathVariable("id") int order_id,
                                                      @RequestParam(name= "id") int item_id,
                                                      @RequestParam(name= "quantity") int quantity) {

        return orderService.addItemToOrder(order_id, item_id, quantity);
    }

    @PutMapping("/{id}/created")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> createdOrder(@PathVariable("id") int order_id) {
        orderService.createdOrder(order_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/items")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<ItemInOrderDTO> getItemsByOrderId(@PathVariable("id") int id){
        return itemInOrderService.findAllByOrderId(id).stream()
                .map(itemInOrderMapper::convertToItemInOrderDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public String getOrderStatus(@PathVariable("id") int id){
        String status = orderService.findOne(id).getStatus().getStatusName();
        return status;
    }


}
