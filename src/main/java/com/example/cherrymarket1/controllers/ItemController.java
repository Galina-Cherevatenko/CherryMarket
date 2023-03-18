package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.dto.ItemDTO;

import com.example.cherrymarket1.models.Item;

import com.example.cherrymarket1.services.CategoryService;
import com.example.cherrymarket1.services.ItemService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    private final ItemService itemService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemController(ItemService itemService, CategoryService categoryService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    public List<ItemDTO> getItems(){
        return itemService.findAll().stream().map(this::convertToItemDTO).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ItemDTO  getItem(@PathVariable("id") int id){
        return convertToItemDTO(itemService.findOne(id));

    }
    @ExceptionHandler
    private ResponseEntity<ItemErrorResponse> handleException(ItemNotFoundException e){
        ItemErrorResponse response=new ItemErrorResponse("Item with this id wasn't found!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ItemDTO itemDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
        {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new ItemNotCreatedException(errorMsg.toString());
        }
        itemService.save(convertToItem(itemDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<ItemErrorResponse> handleException(ItemNotCreatedException e){
        ItemErrorResponse response=new ItemErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ItemDTO update(@RequestBody @Valid ItemDTO itemDTO, BindingResult bindingResult,
                            @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
        {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new ItemNotCreatedException(errorMsg.toString());
        }

        itemService.update(id, convertToItem(itemDTO));
        return itemDTO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

        itemService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/{id}/quantity")
    public int getItemQuantity(@PathVariable("id") int id){
        return itemService.findOne(id).getItemQuantity();
    }
   private Item convertToItem(ItemDTO itemDTO){
       return modelMapper.map(itemDTO, Item.class);
   }
    private ItemDTO convertToItemDTO(Item item){
        return modelMapper.map(item, ItemDTO.class);
    }
}
