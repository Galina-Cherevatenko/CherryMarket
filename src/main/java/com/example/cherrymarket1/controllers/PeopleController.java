package com.example.cherrymarket1.controllers;



import com.example.cherrymarket1.dto.PersonDTO;

import com.example.cherrymarket1.entities.Person;

import com.example.cherrymarket1.mappers.PersonMapper;
import com.example.cherrymarket1.services.OrderService;
import com.example.cherrymarket1.services.PeopleService;
import com.example.cherrymarket1.util.PersonErrorResponse;
import com.example.cherrymarket1.exceptions.PersonNotCreatedException;
import com.example.cherrymarket1.exceptions.PersonNotFoundException;
import com.example.cherrymarket1.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/people")
public class PeopleController {


    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    private final PersonMapper personMapper;
    private final OrderService orderService;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator, OrderService orderService,
                            PersonMapper personMapper) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.orderService = orderService;
        this.personMapper = personMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<PersonDTO> getPeople(){
        return peopleService.findAll().stream().map(personMapper::convertToPersonDTO).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PersonDTO  getPerson(@PathVariable("id") int id){
        return personMapper.convertToPersonDTO(peopleService.findOne(id));

    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public int create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        Person person = personMapper.convertToPerson(personDTO);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        Person createdPerson = peopleService.save(person);

        return createdPerson.getId();
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e){
        PersonErrorResponse response=new PersonErrorResponse("Person with this id wasn't found!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e){
        PersonErrorResponse response=new PersonErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
        {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }
        peopleService.update(id, personMapper.convertToPerson(personDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/{id}/orders")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> createOrder (@PathVariable("id") int person_id){
        orderService.create(person_id);
        return ResponseEntity.ok(HttpStatus.OK);

    }

}
