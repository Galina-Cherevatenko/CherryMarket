package com.example.cherrymarket1.services;

import com.example.cherrymarket1.entities.Category;

import com.example.cherrymarket1.repositories.CategoryRepository;

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
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    Category category1 = new Category(1,"Food");
    Category category2 = new Category(2,"Toys");
    Category category3 = new Category(3,"Books");

    @Test
    public void findAll() {
        List<Category> categoryList = new ArrayList<>(Arrays.asList(category1, category2, category3));
        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> resultList = categoryService.findAll();

        assertNotNull(resultList);
        assertEquals(categoryList.size(),resultList.size());
        assertEquals(categoryList.get(0).getName(),resultList.get(0).getName());
    }

    @Test
    public void findOne() {
        when(categoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));

        Category result = categoryService.findOne(1);

        assertNotNull(result);
        assertEquals(category1.getId(),result.getId());
        assertEquals(category1.getName(),result.getName());
    }

    @Test
    public void findByName() {
        when(categoryRepository.findByName(category1.getName())).thenReturn(Optional.of(category1));

        Category result = categoryService.findByName("Food");

        assertNotNull(result);
        assertEquals(category1,result);
    }

    @Test
    public void save() {
        when(categoryRepository.save(category1)).thenReturn(category1);

        Category result = categoryService.save(category1);

        assertNotNull(result);
        assertEquals(category1.getId(),result.getId());
        assertEquals(category1.getName(),result.getName());
    }
}