package com.example.cherrymarket1.repositories;

import com.example.cherrymarket1.models.Category;

import com.example.cherrymarket1.util.CategoryNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSavingRoundTrip(){
        // given
        Category category1 = new Category("Food");

        // when
        Category result = categoryRepository.save(category1);

        // then
        assertNotNull(result);
    }
    @Test
    public void findByName() {
        Category category1 = categoryRepository.save(new Category("Food"));
        Category category2 = categoryRepository.save(new Category("Toys"));
        Category category3 = categoryRepository.save(new Category("Books"));
        // when
        Category result = categoryRepository.findByName("Food").orElseThrow(CategoryNotFoundException::new);

        // then
        assertNotNull(result);
        assertEquals(category1.getName(), result.getName());
    }
}