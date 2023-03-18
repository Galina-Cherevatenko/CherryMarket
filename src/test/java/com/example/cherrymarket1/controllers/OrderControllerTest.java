package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.models.*;
import com.example.cherrymarket1.services.ItemInOrderService;
import com.example.cherrymarket1.services.OrderService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ItemInOrderService itemInOrderService;
    Person person1 = new Person(1,"Fedor", "Moscow", "123456789123",
            "sdsf@sd.ru");
    Person person2 = new Person(2,"Daria", "Moscow", "145656789123",
            "sryf@sd.ru");
    Category category = new Category (1, "Food");
    Item item1 = new Item(1, "Cake", 10, 5,category);
    Item item2 = new Item(2, "Lemon", 5, 10,category);
    Order order1 = new Order();
    Order order2 = new Order();
    Order order3 = new Order();
    ItemInOrder itemInOrder1 = new ItemInOrder(item1, order1, 2);
    ItemInOrder itemInOrder2 = new ItemInOrder(item2, order1, 3);
    List<ItemInOrder> itemsInOrder = new ArrayList<>(Arrays.asList(itemInOrder1, itemInOrder2));

    @Test
    public void getOrder() throws Exception {
        order1.setId(1);
        order1.setItemsInOrder(itemsInOrder);
        order1.setTotalPrice(35);
        order1.setStatus(Status.BASKET);
        order1.setOwner(person1);
        order2.setId(2);

        when(orderService.findOne(order1.getId())).thenReturn(order1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice", Matchers.is(35)))
                .andExpect(jsonPath("$.status", Matchers.is("BASKET")));
    }


    @Test
    public void getOrdersByOwnerId() throws Exception {
        order1.setId(1);
        order1.setTotalPrice(35);
        order1.setStatus(Status.BASKET);
        order1.setOwner(person1);
        order2.setId(2);
        order2.setTotalPrice(40);
        order2.setStatus(Status.CREATED);
        order2.setOwner(person1);
        order3.setId(3);
        order3.setTotalPrice(10);
        order3.setStatus(Status.BASKET);
        order3.setOwner(person2);

        List<Order> orders = new ArrayList<>(Arrays.asList(order1,order2));
        when(orderService.findAllByOwnerId(person1.getId())).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/owner/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].status", Matchers.containsInAnyOrder("BASKET", "CREATED")))
                .andExpect(jsonPath("$[*].totalPrice", Matchers.containsInAnyOrder(40, 35)));

    }

    @Test
    public void addItemToOrder() throws Exception {
        order1.setId(1);

        when(orderService.addItemToOrder(order1.getId(), item1.getId(), 2)).thenReturn(true);

        ResultActions result = mockMvc.perform(put("/api/orders/1?id=1&quantity=2"));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getItemsByOrderId() throws Exception {
        order1.setId(1);
        order1.setItemsInOrder(itemsInOrder);
        order1.setTotalPrice(35);
        order1.setStatus(Status.BASKET);
        order1.setOwner(person1);

        when(itemInOrderService.findAllByOrderId(order1.getId())).thenReturn(itemsInOrder);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/1/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].quantity", Matchers.containsInAnyOrder(2, 3)));
    }

    @Test
    public void getOrderStatus() throws Exception {
        order1.setId(1);
        order1.setTotalPrice(35);
        order1.setStatus(Status.BASKET);
        order1.setOwner(person1);

        when(orderService.findOne(order1.getId())).thenReturn(order1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/1/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("корзина"));

    }
}