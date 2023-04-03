package com.example.cherrymarket1.services;

import com.example.cherrymarket1.entities.*;
import com.example.cherrymarket1.repositories.ItemInOrderRepository;
import com.example.cherrymarket1.repositories.ItemRepository;
import com.example.cherrymarket1.repositories.OrderRepository;


import com.example.cherrymarket1.util.CustomResponse;
import com.example.cherrymarket1.util.CustomStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ItemInOrderRepository itemInOrderRepository;
    @MockBean
    private PeopleService peopleService;

    Person person1 =new Person(1,"Fedor", "Moscow", "126666789123",
            "sdsf@sd.ru", "test", "ROLE_ADMIN");
    Person person2 = new Person(2,"Daria", "Moscow", "126444789123",
            "sdsf@sd.ru", "test", "ROLE_ADMIN");
    Order order1 = new Order (1, new ArrayList<>(), 0, Status.BASKET, person1);
    Order order2 = new Order (2, new ArrayList<>(), 0, Status.CREATED, person1);
    Order order3 = new Order (3, new ArrayList<>(), 0, Status.BASKET, person2);
    List<Order> orders = new ArrayList<>(Arrays.asList(order1, order2));
    Category category = new Category (1, "Food");
    Item item1 = new Item(1, "Cake", 10, 5,category);
    Item item2 = new Item(2, "Lemon", 5, 10,category);
    ItemInOrder itemInOrder1 = new ItemInOrder(item1, order1, 2);
    ItemInOrder itemInOrder2 = new ItemInOrder(item2, order1, 3);
    List<ItemInOrder> itemsInOrder = new ArrayList<>(Arrays.asList(itemInOrder1, itemInOrder2));
    @Test
    public void findOne() {
        when(orderRepository.findById(order1.getId())).thenReturn(Optional.of(order1));

        Order result = orderService.findOne(1);

        assertNotNull(result);
        assertEquals(order1.getId(),result.getId());
        assertEquals(order1,result);
    }

    @Test
    public void findAllByOwnerId() {
        when(peopleService.findOne(person1.getId())).thenReturn(person1);
        when(orderRepository.findByOwner(person1)).thenReturn(orders);

        List<Order> resultList = orderService.findAllByOwnerId(person1.getId());

        assertNotNull(resultList);
        assertEquals(orders.size(),resultList.size());
        assertEquals(orders.get(0),resultList.get(0));
    }

   @Test
    public void addItemToOrder() {
        when(itemRepository.findById(item1.getId())).thenReturn(Optional.of(item1));
        when(orderRepository.findById(order1.getId())).thenReturn(Optional.of(order1));
        when(itemInOrderRepository.save(itemInOrder1)).thenReturn(itemInOrder1);
        CustomResponse successResponse = new CustomResponse(CustomStatus.SUCCESS);

        CustomResponse result = orderService.addItemToOrder(order1.getId(), item1.getId(),2);

        assertEquals(successResponse.getMessage(),result.getMessage());

    }

}