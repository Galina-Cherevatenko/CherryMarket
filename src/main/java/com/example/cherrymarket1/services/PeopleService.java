package com.example.cherrymarket1.services;

import com.example.cherrymarket1.entities.Person;
import com.example.cherrymarket1.mappers.PersonMapper;
import com.example.cherrymarket1.repositories.PeopleRepository;
import com.example.cherrymarket1.exceptions.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PersonMapper personMapper, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.personMapper = personMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }

    public Optional<Person> findByPhone(String phone) {
        return peopleRepository.findByPhone(phone);
    }
    public Optional<Person> findByName(String name) {
        return peopleRepository.findByName(name);
    }

    public Person save(Person person){
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        person.setRole("ROLE_USER");
        return peopleRepository.save(person);

    }

    public Person update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        return peopleRepository.save(updatedPerson);
    }

    public void delete(int id){
        peopleRepository.deleteById(id);
    }

}

