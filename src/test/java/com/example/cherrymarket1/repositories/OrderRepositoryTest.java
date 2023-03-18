package com.example.cherrymarket1.repositories;

import com.example.cherrymarket1.models.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSavingRoundTrip(){
        // given
        Person person1 = entityManager.persist(new Person("Fedor", "Moscow", "123456789123",
                "sdsf@sd.ru"));;
        Order order = new Order (new ArrayList<>(), 0, Status.BASKET, person1);

        // when
        Order result = orderRepository.save(order);

        // then
        assertNotNull(result);

    }

    @Test
    public void findByOwner() {
        // given
        Person person1 = entityManager.persist(new Person("Fedor", "Moscow", "123456789123",
                "sdsf@sd.ru"));
        Person person2 = entityManager.persist(new Person("Daria", "Moscow", "145656789123",
                "sryf@sd.ru"));
        Order order1 = orderRepository.save(new Order (new ArrayList<>(), 0, Status.BASKET, person1));
        Order order2 = orderRepository.save(new Order(new ArrayList<>(), 0, Status.BASKET, person1));
        Order order3 = orderRepository.save(new Order(new ArrayList<>(), 0, Status.BASKET, person2));

        List<Order> orders = new ArrayList<>(Arrays.asList(order1, order2));

        // when
        List<Order> resultList = orderRepository.findByOwner(person1);

        // then
        assertNotNull(resultList);
        assertEquals(orders.size(),resultList.size());
        assertEquals(orders.get(0).getOwner(),resultList.get(0).getOwner());
    }


}