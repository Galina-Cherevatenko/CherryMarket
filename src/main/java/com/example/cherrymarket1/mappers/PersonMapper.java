package com.example.cherrymarket1.mappers;

import com.example.cherrymarket1.dto.PersonDTO;
import com.example.cherrymarket1.entities.Person;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PersonMapper {

    private ModelMapper modelMapper;
    public PersonMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Person convertToPerson (PersonDTO personDTO) {
        return Objects.isNull(personDTO) ? null: modelMapper.map(personDTO, Person.class);
    }

    public PersonDTO convertToPersonDTO (Person person) {
        return Objects.isNull(person) ? null: modelMapper.map(person, PersonDTO.class);
    }
}
