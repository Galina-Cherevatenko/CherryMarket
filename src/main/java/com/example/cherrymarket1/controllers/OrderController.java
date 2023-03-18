package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.dto.*;
import com.example.cherrymarket1.models.ItemInOrder;
import com.example.cherrymarket1.models.Order;
import com.example.cherrymarket1.services.ItemInOrderService;
import com.example.cherrymarket1.services.OrderService;
import com.example.cherrymarket1.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final ItemInOrderService itemInOrderService;
    private final ModelMapper modelMapper;
    @Autowired
    public OrderController(OrderService orderService, ItemInOrderService itemInOrderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.itemInOrderService = itemInOrderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable("id") int id){
        return convertToOrderDTO(orderService.findOne(id));

    }

    @GetMapping("/owner/{id}")
    public List<OrderDTO> getOrdersByOwnerId(@PathVariable("id") int id){
        return orderService.findAllByOwnerId(id).stream()
                .map(this::convertToOrderDTO).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> addItemToOrder(@PathVariable("id") int order_id,
                                                     @RequestParam(name= "id") int item_id,
                                                     @RequestParam(name= "quantity") int quantity) {
        if(!(orderService.addItemToOrder(order_id, item_id, quantity)))
            throw new ItemNotAddedException();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}/created")
    public ResponseEntity<HttpStatus> createdOrder(@PathVariable("id") int order_id) {
        orderService.createdOrder(order_id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<OrderErrorResponse> handleException(ItemNotAddedException e){
        OrderErrorResponse response=new OrderErrorResponse("Item was not added!");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/items")
    public List<ItemInOrderDTO> getItemsByOrderId(@PathVariable("id") int id){
        return itemInOrderService.findAllByOrderId(id).stream()
                .map(this::convertToItemInOrderDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}/status")
    public String getOrderStatus(@PathVariable("id") int id){
        String status = orderService.findOne(id).getStatus().getStatusName();
        return status;
    }

    private Order convertToOrder(OrderDTO orderDTO){
        return modelMapper.map(orderDTO, Order.class);
    }
    private OrderDTO convertToOrderDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }
    private ItemInOrderDTO convertToItemInOrderDTO(ItemInOrder itemInOrder){
        return modelMapper.map(itemInOrder, ItemInOrderDTO.class);
    }
}
