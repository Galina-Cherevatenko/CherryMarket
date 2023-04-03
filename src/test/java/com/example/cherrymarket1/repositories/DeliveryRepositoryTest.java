package com.example.cherrymarket1.repositories;

import com.example.cherrymarket1.entities.Delivery;
import com.example.cherrymarket1.entities.Order;
import com.example.cherrymarket1.entities.Person;
import com.example.cherrymarket1.entities.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeliveryRepositoryTest {
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByOrder() {

        Person person1 = entityManager.persist(new Person("Fedor", "Moscow", "126666789123",
                "sdsf@sd.ru", "test", "ROLE_ADMIN"));
        Order order1 = entityManager.persist(new Order (new ArrayList<>(), 0, Status.BASKET, person1));
        Delivery delivery1 = deliveryRepository.save(new Delivery(order1, LocalDateTime.now(), LocalDateTime.now()));

        // when
        Delivery result = deliveryRepository.findByOrder(order1);

        // then
        assertNotNull(result);
        assertEquals(delivery1.getOrder(),order1);

    }
}