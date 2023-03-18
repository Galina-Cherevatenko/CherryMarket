package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.dto.PersonDTO;
import com.example.cherrymarket1.models.Person;
import com.example.cherrymarket1.services.OrderService;
import com.example.cherrymarket1.services.PeopleService;
import com.example.cherrymarket1.util.PersonValidator;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.Arrays;


import static org.mockito.Mockito.when;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PeopleController.class)
public class PeopleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PeopleService peopleService;

    @MockBean
    private PersonValidator personValidator;

    @MockBean
    private OrderService orderService;

    Person person1 = new Person(1,"Fedor", "Moscow", "123456789123",
            "sdsf@sd.ru");
    Person person2 = new Person(2,"Daria", "Moscow", "145656789123",
            "sryf@sd.ru");
    Person person3 = new Person(3, "Maria", "Moscow", "198756789123",
            "sryf@sd.ru");

    PersonDTO person1DTO = new PersonDTO("Fedor", "Moscow", "123456789123",
            "sdsf@sd.ru");

    @Test
    public void getPeople() throws Exception {
        when(peopleService.findAll()).thenReturn(new ArrayList<>(Arrays.asList(person1, person2, person3)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/people"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(3)))
            .andExpect(jsonPath("$[*].name", Matchers.containsInAnyOrder("Daria", "Fedor", "Maria")));

    }

    @Test
    public void getPerson() throws Exception {
        when(peopleService.findOne(person1.getId())).thenReturn(person1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/people/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Fedor")));
        }

    @Test
    public void create() throws Exception {

        when(peopleService.save(person1)).thenReturn(person1);

        ResultActions result = mockMvc.perform(post("/api/people")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person1DTO))
                .accept(APPLICATION_JSON)
        );

        // then
        result
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fedor"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void update() throws Exception {

        Person personNew = new Person("NewName", "Moscow", "123456789123", "sdsf@sd.ru");
        PersonDTO personNewDTO = new PersonDTO("NewName", "Moscow", "123456789123", "sdsf@sd.ru");
        Person updatedPerson = new Person(1, "NewName", "Moscow", "123456789123",
                "sdsf@sd.ru");

        when(peopleService.update(1, personNew)).thenReturn(updatedPerson);

        ResultActions result = mockMvc.perform(put("/api/people/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personNewDTO))
                .accept(APPLICATION_JSON)
        );

        // then
        result
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("NewName"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}