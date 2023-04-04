package com.example.cherrymarket1.controllers;
import com.example.cherrymarket1.config.SecurityConfig;
import com.example.cherrymarket1.entities.Order;
import com.example.cherrymarket1.entities.Person;
import com.example.cherrymarket1.entities.Status;
import com.example.cherrymarket1.mappers.OrderMapper;
import com.example.cherrymarket1.security.PersonDetailsRepository;
import com.example.cherrymarket1.services.DeliveryService;
import com.example.cherrymarket1.services.OrderService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DeliveryController.class)
@Import(SecurityConfig.class)
public class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeliveryService deliveryService;

    @MockBean
    private PersonDetailsRepository personDetailsRepository;
    @MockBean
    private OrderService orderService;

    Person person1 = new Person(1,"Fedor", "Moscow", "126666789123",
            "sdsf@sd.ru", "test", "ROLE_USER");

    Order order1 = new Order (1, new ArrayList<>(), 0, Status.BASKET, person1);
    Order order2 = new Order (2, new ArrayList<>(), 0, Status.CREATED, person1);
    Order order3 = new Order (3, new ArrayList<>(), 0, Status.CREATED, person1);

    @Test
    @WithMockUser(roles = {"COURIER"})
    public void getOrdersByStatusCreated() throws Exception {
        List<Order> ordersCreated = new ArrayList<>(Arrays.asList(order2, order3));

        when(orderService.findAll()).thenReturn(ordersCreated);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/delivery/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].status", Matchers.containsInAnyOrder("CREATED", "CREATED")));

    }

    @Test
    @WithMockUser(roles = {"COURIER"})
    public void takeOrderToDelivery() throws Exception {
        when(deliveryService.startOrderDelivery(order2.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery/2/start"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"COURIER"})
    public void finishOrderDelivery() throws Exception {
        when(deliveryService.finishOrderDelivery(order2.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/delivery/2/finish"))
                .andExpect(status().isOk());
    }
}