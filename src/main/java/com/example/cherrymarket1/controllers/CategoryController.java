package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.dto.CategoryDTO;
import com.example.cherrymarket1.models.Category;
import com.example.cherrymarket1.services.CategoryService;
import com.example.cherrymarket1.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final CategoryValidator categoryValidator;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper, CategoryValidator categoryValidator) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.categoryValidator = categoryValidator;
    }

    @PostMapping()
    public Category create(@RequestBody @Valid CategoryDTO categoryDTO, BindingResult bindingResult) {
        Category category = convertToCategory(categoryDTO);
        categoryValidator.validate(category, bindingResult);
        if (bindingResult.hasErrors())
        {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new CategoryNotCreatedException(errorMsg.toString());
        }
        return categoryService.save(category);
    }

    @ExceptionHandler
    private ResponseEntity<CategoryErrorResponse> handleException(CategoryNotCreatedException e){
        CategoryErrorResponse response=new CategoryErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        categoryService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Category convertToCategory(CategoryDTO categoryDTO){
        return modelMapper.map(categoryDTO, Category.class);
    }
    private CategoryDTO convertToCategoryDTO(Category category){
        return modelMapper.map(category, CategoryDTO.class);
    }
}

