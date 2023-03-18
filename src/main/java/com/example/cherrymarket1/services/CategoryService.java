package com.example.cherrymarket1.services;

import com.example.cherrymarket1.models.Category;

import com.example.cherrymarket1.repositories.CategoryRepository;

import com.example.cherrymarket1.util.CategoryNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findOne(int id) {
        Optional<Category> foundCategory = categoryRepository.findById(id);
        return foundCategory.orElseThrow(CategoryNotFoundException::new);
    }
    public Category findByName(String name) {
        Optional<Category> foundCategory = categoryRepository.findByName(name);
        return foundCategory.orElseThrow(CategoryNotFoundException::new);

    }

    @Transactional
    public Category save(Category category){
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(int id){
        categoryRepository.deleteById(id);
    }

}

