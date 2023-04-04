package com.example.cherrymarket1.controllers;

import com.example.cherrymarket1.config.SecurityConfig;
import com.example.cherrymarket1.dto.PersonDTO;
import com.example.cherrymarket1.entities.Person;
import com.example.cherrymarket1.mappers.PersonMapper;
import com.example.cherrymarket1.security.PersonDetailsRepository;
import com.example.cherrymarket1.services.OrderService;
import com.example.cherrymarket1.services.PeopleService;
import com.example.cherrymarket1.util.PersonValidator;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PeopleController.class)
@Import(SecurityConfig.class)
@WithMockUser(roles = {"ADMIN"})
public class PeopleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PeopleService peopleService;

    @MockBean
    private PersonValidator personValidator;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PersonDetailsRepository personDetailsRepository;

    Person person1 =new Person(1,"Fedor", "Moscow", "123456789123",
            "sdsf@sd.ru", "test", "ROLE_USER");
    Person person2 = new Person(2,"Daria", "Moscow", "126444789123",
            "sdsf@sd.ru", "test", "ROLE_ADMIN");
    Person person3 = new Person(3, "Maria", "Moscow", "198756789123",
            "sdsf@sd.ru", "test", "ROLE_USER");;

    PersonDTO person1DTO = new PersonDTO("Fedor", "Moscow", "123456789123",
            "sdsf@sd.ru", "test");

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
                .andExpect(MockMvcResultMatchers.content().string("1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void update() throws Exception {

        Person personNew = new Person("NewName", "Moscow", "123456789123",
                "sdsf@sd.ru", "test", "ROLE_USER");
        PersonDTO personNewDTO = new PersonDTO("NewName", "Moscow", "123456789123",
                "sdsf@sd.ru","test" );
        Person updatedPerson = new Person(1, "NewName", "Moscow", "123456789123",
                "sdsf@sd.ru", "test", "ROLE_USER");

        when(peopleService.update(1, personNew)).thenReturn(updatedPerson);

        ResultActions result = mockMvc.perform(put("/api/people/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personNewDTO))
                .accept(APPLICATION_JSON)
        );

        // then
        result
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}