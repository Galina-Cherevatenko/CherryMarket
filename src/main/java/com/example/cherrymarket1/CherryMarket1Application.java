package com.example.cherrymarket1;

import com.example.cherrymarket1.mappers.ItemInOrderMapper;
import com.example.cherrymarket1.mappers.ItemMapper;
import com.example.cherrymarket1.mappers.OrderMapper;
import com.example.cherrymarket1.mappers.PersonMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CherryMarket1Application {

    public static void main(String[] args) {
        SpringApplication.run(CherryMarket1Application.class, args);
    }

    @Bean
    public PersonMapper personMapper(){
        return new PersonMapper();
    }
    @Bean
    public OrderMapper orderMapper(){
        return new OrderMapper();
    }
    @Bean
    public ItemMapper itemMapper(){
        return new ItemMapper();
    }
    @Bean
    public ItemInOrderMapper itemInOrderMapper(){
        return new ItemInOrderMapper();
    }
}


