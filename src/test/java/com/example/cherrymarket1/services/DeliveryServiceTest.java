package com.example.cherrymarket1.services;

import com.example.cherrymarket1.entities.Delivery;
import com.example.cherrymarket1.entities.Order;
import com.example.cherrymarket1.entities.Person;
import com.example.cherrymarket1.entities.Status;
import com.example.cherrymarket1.repositories.DeliveryRepository;

import com.example.cherrymarket1.repositories.OrderRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliveryServiceTest {
    @Autowired
    private DeliveryService deliveryService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private DeliveryRepository deliveryRepository;

    @MockBean
    private OrderService orderService;

    Person person1 =new Person(1,"Fedor", "Moscow", "126666789123",
            "sdsf@sd.ru", "test", "ROLE_ADMIN");

    Order order1 = new Order (1, new ArrayList<>(), 0, Status.CREATED, person1);
    Order order2 = new Order (2, new ArrayList<>(), 0, Status.INDELIVERY, person1);
    Delivery delivery1 = new Delivery(order1, LocalDateTime.now(), LocalDateTime.now());
    Delivery delivery2 = new Delivery(order2, LocalDateTime.now(), LocalDateTime.now());

    @Test
    public void startOrderDelivery() {
        when(orderService.findOne(order1.getId())).thenReturn(order1);

        Boolean result = deliveryService.startOrderDelivery(order1.getId());

        assertTrue(result);
    }

    @Test
    public void finishOrderDelivery() {
        when(orderService.findOne(order2.getId())).thenReturn(order2);
        when(deliveryRepository.findByOrder(order2)).thenReturn(delivery2);

        Boolean result = deliveryService.finishOrderDelivery(order2.getId());

        assertTrue(result);
    }
}