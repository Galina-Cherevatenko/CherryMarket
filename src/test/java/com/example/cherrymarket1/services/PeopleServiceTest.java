package com.example.cherrymarket1.services;


import com.example.cherrymarket1.entities.Person;
import com.example.cherrymarket1.repositories.PeopleRepository;

import com.example.cherrymarket1.exceptions.PersonNotFoundException;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PeopleServiceTest {
    @Autowired
    private PeopleService peopleService;
    @MockBean
    private PeopleRepository peopleRepository;

    Person person1 =new Person(1,"Fedor", "Moscow", "126666789123",
            "sdsf@sd.ru", "test", "ROLE_ADMIN");
    Person person2 = new Person(2,"Daria", "Moscow", "126444789123",
            "sdsf@sd.ru", "test", "ROLE_ADMIN");
    Person person3 = new Person(3, "Maria", "Moscow", "198756789123",
            "sdsf@sd.ru", "test", "ROLE_USER");
    @Test
    public void save() {

        when(peopleRepository.save(person1)).thenReturn(person1);

        Person result = peopleService.save(person1);

        assertNotNull(result);
        assertEquals(person1.getId(),result.getId());
        assertEquals(person1,result);
    }

    @Test
    public void findAll() {
        List<Person> personList = new ArrayList<>(Arrays.asList(person1, person2, person3));
        when(peopleRepository.findAll()).thenReturn(personList);

        List<Person> resultList = peopleService.findAll();

        assertNotNull(resultList);
        assertEquals(personList.size(),resultList.size());
        assertEquals(personList.get(0),resultList.get(0));
    }

    @Test
    public void findOne() {
        when(peopleRepository.findById(person1.getId())).thenReturn(Optional.of(person1));

        Person result = peopleService.findOne(1);

        assertNotNull(result);
        assertEquals(person1.getId(),result.getId());
        assertEquals(person1,result);
    }

    @Test
    public void findByPhone() {
        when(peopleRepository.findByPhone(person1.getPhone())).thenReturn(Optional.of(person1));

        Optional<Person> foundPerson = peopleService.findByPhone("126666789123");
        Person result = foundPerson.orElseThrow(PersonNotFoundException::new);

        assertNotNull(result);
        assertEquals(person1,result);
    }

    @Test
    public void update() {
        Person personNew = new Person("NewName", "Moscow", "123456789123",
                "sdsf@sd.ru", "test", "ROLE_USER");
        Person updatedPerson = new Person(1, "NewName", "Moscow", "123456789123",
                "sdsf@sd.ru", "test", "ROLE_USER");

        when(peopleRepository.save(updatedPerson)).thenReturn(updatedPerson);

        Person result = peopleService.update(1, personNew);

        assertNotNull(result);
        assertEquals(updatedPerson.getId(),result.getId());
        assertEquals(updatedPerson,result);
    }
}