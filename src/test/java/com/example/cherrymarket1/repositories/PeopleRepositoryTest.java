package com.example.cherrymarket1.repositories;

import com.example.cherrymarket1.models.Person;
import com.example.cherrymarket1.util.PersonNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;



import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PeopleRepositoryTest {
    @Autowired
    private PeopleRepository peopleRepository;

    @Test
    public void testSavingRoundTrip(){
        // given
        Person person = new Person("SomeName", "Moscow", "123456789123", "sdsf@sd.ru");

        // when
        Person result = peopleRepository.save(person);

        // then
        assertNotNull(result);
    }
    @Test
    public void findByPhone() {
        // given
        Person person1 = peopleRepository.save(new Person("Fedor", "Moscow", "123456789123",
                "sdsf@sd.ru"));
        Person person2 = peopleRepository.save(new Person("Daria", "Moscow", "145656789123",
                "sryf@sd.ru"));
        Person person3 = peopleRepository.save(new Person("Maria", "Moscow", "198756789123",
                "sryf@sd.ru"));
        // when
        Person result = peopleRepository.findByPhone("145656789123").orElseThrow(PersonNotFoundException::new);

        // then
        assertNotNull(result);
        assertEquals(person2, result);
    }
}