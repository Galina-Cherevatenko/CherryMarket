package com.example.cherrymarket1.repositories;

import com.example.cherrymarket1.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByPhone(String phone);
    Optional<Person> findByName(String name);


}

