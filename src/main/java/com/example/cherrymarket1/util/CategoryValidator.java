package com.example.cherrymarket1.util;

import com.example.cherrymarket1.models.Category;

import com.example.cherrymarket1.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Category.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Category category = (Category) o;
        if (categoryRepository.findByName(category.getName()).isPresent())
            errors.rejectValue("name", "", "Такая категоря уже существует");

    }
}
