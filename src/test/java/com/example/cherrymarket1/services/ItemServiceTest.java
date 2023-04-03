package com.example.cherrymarket1.services;

import com.example.cherrymarket1.entities.Category;
import com.example.cherrymarket1.entities.Item;

import com.example.cherrymarket1.repositories.ItemRepository;

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
public class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    Category category = new Category(1,"Food");

    Item item1 = new Item(1, "Cake", 10, 5,category);
    Item item2 = new Item(2, "Lemon", 5, 10,category);

    @Test
    public void findAll() {
        List<Item> itemList = new ArrayList<>(Arrays.asList(item1, item2));
        when(itemRepository.findAll()).thenReturn(itemList);

        List<Item> resultList = itemService.findAll();

        assertNotNull(resultList);
        assertEquals(itemList.size(),resultList.size());
        assertEquals(itemList.get(0).getItemName(),resultList.get(0).getItemName());
    }

    @Test
    public void findOne() {
        when(itemRepository.findById(item1.getId())).thenReturn(Optional.of(item1));

        Item result = itemService.findOne(1);

        assertNotNull(result);
        assertEquals(item1.getId(),result.getId());
        assertEquals(item1.getItemName(),result.getItemName());
    }
}