package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.models.Order;
import com.example.cherrymarket1.models.Person;
import com.example.cherrymarket1.models.Status;
import com.example.cherrymarket1.services.DeliveryService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DeliveryController.class)
public class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeliveryService deliveryService;

    @MockBean
    private OrderService orderService;
    Person person1 = new Person(1,"Fedor", "Moscow", "123456789123",
            "sdsf@sd.ru");
    Order order1 = new Order (1, new ArrayList<>(), 0, Status.BASKET, person1);
    Order order2 = new Order (2, new ArrayList<>(), 0, Status.CREATED, person1);
    Order order3 = new Order (3, new ArrayList<>(), 0, Status.CREATED, person1);

    @Test
    public void getOrdersByStatusCreated() throws Exception {
        List<Order> ordersCreated = new ArrayList<>(Arrays.asList(order2, order3));

        when(orderService.findAll()).thenReturn(ordersCreated);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].status", Matchers.containsInAnyOrder("CREATED", "CREATED")));

    }

    @Test
    public void takeOrderToDelivery() throws Exception {
        when(deliveryService.startOrderDelivery(order2.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery/2/start"))
                .andExpect(status().isOk());
    }

    @Test
    public void finishOrderDelivery() throws Exception {
        when(deliveryService.finishOrderDelivery(order2.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery/2/finish"))
                .andExpect(status().isOk());
    }
}