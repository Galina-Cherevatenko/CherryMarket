package com.example.cherrymarket1.controllers;



import com.example.cherrymarket1.dto.PersonDTO;

import com.example.cherrymarket1.models.Person;

import com.example.cherrymarket1.services.OrderService;
import com.example.cherrymarket1.services.PeopleService;
import com.example.cherrymarket1.util.PersonErrorResponse;
import com.example.cherrymarket1.util.PersonNotCreatedException;
import com.example.cherrymarket1.util.PersonNotFoundException;
import com.example.cherrymarket1.util.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ModelMapper modelMapper;
    private final OrderService orderService;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator, OrderService orderService,
                            ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PersonDTO> getPeople(){
        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public PersonDTO  getPerson(@PathVariable("id") int id){
        return convertToPersonDTO(peopleService.findOne(id));

    }

    @PostMapping()
    public Person create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);
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

        return peopleService.save(person);
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
    public Person update(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult,
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

        return peopleService.update(id, convertToPerson(personDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {

        peopleService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/{id}/orders")
    public ResponseEntity<HttpStatus> createOrder (@PathVariable("id") int person_id){
        orderService.create(person_id);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    private Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }
    private PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }
}
