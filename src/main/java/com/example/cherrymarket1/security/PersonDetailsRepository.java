package com.example.cherrymarket1.security;

import com.example.cherrymarket1.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonDetailsRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByName(String name);


}
