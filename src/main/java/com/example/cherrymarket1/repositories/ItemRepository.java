package com.example.cherrymarket1.repositories;


import com.example.cherrymarket1.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
