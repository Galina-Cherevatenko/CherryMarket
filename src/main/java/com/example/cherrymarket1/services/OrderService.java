package com.example.cherrymarket1.services;

import com.example.cherrymarket1.entities.*;
import com.example.cherrymarket1.repositories.ItemInOrderRepository;
import com.example.cherrymarket1.repositories.ItemRepository;
import com.example.cherrymarket1.repositories.OrderRepository;
import com.example.cherrymarket1.exceptions.ItemNotFoundException;
import com.example.cherrymarket1.exceptions.OrderNotFoundException;
import com.example.cherrymarket1.util.CustomResponse;
import com.example.cherrymarket1.util.CustomStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ItemInOrderRepository itemInOrderRepository;
    private final ItemService itemService;
    private final PeopleService peopleService;


    @Autowired
    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, ItemInOrderRepository itemInOrderRepository, ItemService itemService, PeopleService peopleService) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.itemInOrderRepository = itemInOrderRepository;
        this.itemService = itemService;
        this.peopleService = peopleService;
    }

    public void save(Order order){
        orderRepository.save(order);
    }

    public Order findOne(int id) {
        Optional<Order> foundOrder = orderRepository.findById(id);
        return foundOrder.orElseThrow(OrderNotFoundException::new);
    }

    public List<Order> findAllByOwnerId (int person_id){
        List<Order> personOrders = orderRepository.findByOwner(peopleService.findOne(person_id));
        return personOrders;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public CustomResponse addItemToOrder(int order_id, int item_id, int quantity){
        Optional <Item> foundItem = itemRepository.findById(item_id);
        Item item = foundItem.orElseThrow(ItemNotFoundException::new);
        if (item.getItemQuantity()>=quantity){
            Optional <Order> foundOrder = orderRepository.findById(order_id);
            Order order = foundOrder.orElseThrow(OrderNotFoundException::new);
            ItemInOrder itemInOrder = new ItemInOrder();
            itemInOrder.setOrder(order);
            itemInOrder.setItem(item);
            itemInOrder.setQuantity(quantity);
            item.setItemQuantity(item.getItemQuantity()- quantity);
            itemInOrder = itemInOrderRepository.save(itemInOrder);
            setPriceToOrder(order);
            return new CustomResponse(CustomStatus.SUCCESS);
        }
       return new CustomResponse(CustomStatus.EXCEPTION);
    }

    @Transactional
    public void create(int person_id) {
        Status status = Status.BASKET;
        List<ItemInOrder> itemsInOrder = new ArrayList<>();
        int totalPrice=0;
        Person owner = peopleService.findOne(person_id);
        Order order = new Order (itemsInOrder, totalPrice, status, owner);
        orderRepository.save(order);
    }
    @Transactional
    public void createdOrder(int id){
        Optional <Order> foundOrder = orderRepository.findById(id);
        Order order = foundOrder.orElseThrow(OrderNotFoundException::new);
        order.setStatus(Status.CREATED);
        orderRepository.save(order);
    }

    public void update (int id, Order updatedOrder){
        updatedOrder.setId(id);
        setPriceToOrder(updatedOrder);
        orderRepository.save(updatedOrder);
    }
    public void setPriceToOrder(Order order){
        List<ItemInOrder> itemsInOrder = order.getItemsInOrder();
        int totalPrice=0;
        for(ItemInOrder itemInOrder:itemsInOrder) {
            Item item = itemInOrder.getItem();
            totalPrice += item.getPrice()*itemInOrder.getQuantity();
        }
        order.setTotalPrice(totalPrice);
    }
}
