package com.example.cherrymarket1.services;

import com.example.cherrymarket1.models.Person;
import com.example.cherrymarket1.repositories.PeopleRepository;
import com.example.cherrymarket1.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
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

    @Transactional
    public Person save(Person person){
        return peopleRepository.save(person);

    }

    @Transactional
    public Person update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        return peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

}

