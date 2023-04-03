package com.example.cherrymarket1.util;




import com.example.cherrymarket1.entities.Person;
import com.example.cherrymarket1.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (peopleService.findByPhone(person.getPhone()).isPresent())
            errors.rejectValue("phone", "", "Такой телефон уже существует");
        if (peopleService.findByName(person.getName()).isPresent())
            errors.rejectValue("name", "", "Пользователь с таким именем уже существует");

    }
}
