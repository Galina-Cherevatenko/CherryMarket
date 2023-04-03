package com.example.cherrymarket1.services;

import com.example.cherrymarket1.entities.*;
import com.example.cherrymarket1.repositories.ItemInOrderRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemInOrderServiceTest {
    @Autowired
    private ItemInOrderService itemInOrderService;

    @MockBean
    private ItemInOrderRepository itemInOrderRepository;
    @MockBean
    private OrderService orderService;

    Person person1 = new Person(1, "Fedor", "Moscow", "126666789123",
            "sdsf@sd.ru", "test", "ROLE_ADMIN");
    Category category = (new Category(1, "Food"));
    Item item1 = new Item(1, "Cake", 10, 5,category);
    Item item2 = new Item(2,"Lemon", 5, 10,category);
    Order order1 = new Order (new ArrayList<>(), 0, Status.BASKET, person1);

    ItemInOrder itemInOrder1 = new ItemInOrder(item1, order1, 2);
    ItemInOrder itemInOrder2 = new ItemInOrder(item2, order1, 1);

    @Test
    public void findAllByOrderId() {
        List<ItemInOrder> itemInOrderList = new ArrayList<>(Arrays.asList(itemInOrder1, itemInOrder2));
        when(orderService.findOne(order1.getId())).thenReturn(order1);
        when(itemInOrderRepository.findByOrder(order1)).thenReturn(itemInOrderList);

        List<ItemInOrder> resultList = itemInOrderService.findAllByOrderId(order1.getId());

        assertNotNull(resultList);
        assertEquals(itemInOrderList.size(),resultList.size());
        assertEquals(itemInOrderList.get(0).getQuantity(),resultList.get(0).getQuantity());

    }
}