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
public class ItemInOrderRepositoryTest {
    @Autowired
    private ItemInOrderRepository itemInOrderRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void findByOrder() {
        Person person1 = entityManager.persist(new Person("Fedor", "Moscow", "123456789123",
                "sdsf@sd.ru"));
        Category category = entityManager.persist(new Category("Food"));
        Item item1 = entityManager.persist(new Item("Cake", 10, 5,category));
        Item item2 = entityManager.persist(new Item("Lemon", 5, 10,category));
        Order order1 = entityManager.persist(new Order (new ArrayList<>(), 0, Status.BASKET, person1));
        Order order2 = entityManager.persist(new Order(new ArrayList<>(), 0, Status.BASKET, person1));
        ItemInOrder itemInOrder1 = itemInOrderRepository.save(new ItemInOrder(item1, order1, 2));
        ItemInOrder itemInOrder2 = itemInOrderRepository.save(new ItemInOrder(item2, order1, 1));
        List<ItemInOrder> itemInOrderList = new ArrayList<>(Arrays.asList(itemInOrder1, itemInOrder2));

        // when
        List<ItemInOrder> resultList = itemInOrderRepository.findByOrder(order1);

        // then
        assertNotNull(resultList);
        assertEquals(itemInOrderList.size(),resultList.size());
        assertEquals(itemInOrderList.get(0).getQuantity(),resultList.get(0).getQuantity());
    }
}