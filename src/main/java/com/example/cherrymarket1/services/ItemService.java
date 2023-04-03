package com.example.cherrymarket1.services;

import com.example.cherrymarket1.entities.Item;
import com.example.cherrymarket1.repositories.ItemRepository;
import com.example.cherrymarket1.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryService categoryService) {
        this.itemRepository = itemRepository;
        this.categoryService = categoryService;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findOne(int id) {
        Optional<Item> foundItem = itemRepository.findById(id);
        return foundItem.orElseThrow(ItemNotFoundException::new);
    }

    public void save(Item item){
        enrichItem(item);
        itemRepository.save(item);
    }

    public Item update(int id, Item updatedItem){
        enrichItem(updatedItem);
        updatedItem.setId(id);
        return itemRepository.save(updatedItem);
    }

    public void delete(int id){
        itemRepository.deleteById(id);
    }

    public void enrichItem (Item item){
        item.setCategory(categoryService.findByName(item.getCategory().getName()));
    }
}
