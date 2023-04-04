package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.dto.CategoryDTO;
import com.example.cherrymarket1.dto.ItemDTO;

import com.example.cherrymarket1.entities.Category;
import com.example.cherrymarket1.entities.Item;

import com.example.cherrymarket1.mappers.ItemMapper;
import com.example.cherrymarket1.security.PersonDetailsRepository;
import com.example.cherrymarket1.services.CategoryService;
import com.example.cherrymarket1.services.ItemService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.cherrymarket1.config.SecurityConfig;
import org.springframework.context.annotation.Import;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
@Import(SecurityConfig.class)
@WithMockUser(roles = {"ADMIN"})
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private PersonDetailsRepository personDetailsRepository;
    Category category = new Category(1,"Food");

    Item item1 = new Item(1, "Cake", 10, 5,category);
    Item item2 = new Item(2, "Lemon", 5, 10,category);

    @Test
    public void getItems() throws Exception {
        when(itemService.findAll()).thenReturn(new ArrayList<>(Arrays.asList(item1, item2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].itemName", Matchers.containsInAnyOrder("Cake", "Lemon")));
    }

    @Test
    public void getItem() throws Exception {

        when(itemService.findOne(item1.getId())).thenReturn(item1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/item/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName", Matchers.is("Cake")));
    }


    @Test
    public void update() throws Exception {

        Item itemNew = new Item ("SuperFood", 10, 5,category);
        ItemDTO itemNewDTO = new ItemDTO("SuperFood", 10, 5, new CategoryDTO("Food"));
        Item updatedItem = new Item (1, "SuperFood", 10, 5,category);
        when(itemService.update(1, itemNew)).thenReturn(updatedItem);

        ResultActions result = mockMvc.perform(put("/api/item/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemNewDTO))
                .accept(APPLICATION_JSON)
        );

        // then
        result
                .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value("SuperFood"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getItemQuantity() throws Exception {
        when(itemService.findOne(item1.getId())).thenReturn(item1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/item/1/quantity"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

}