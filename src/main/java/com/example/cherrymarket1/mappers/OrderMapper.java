package com.example.cherrymarket1.mappers;

import com.example.cherrymarket1.dto.OrderDTO;

import com.example.cherrymarket1.entities.Order;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderMapper {

    private ModelMapper modelMapper;

    public OrderMapper() {
        this.modelMapper = new ModelMapper();
    }


    public Order convertToOrder (OrderDTO orderDTO) {
        return Objects.isNull(orderDTO) ? null: modelMapper.map(orderDTO, Order.class);
    }

    public OrderDTO convertToOrderDTO (Order order) {
        return Objects.isNull(order) ? null: modelMapper.map(order, OrderDTO.class);
    }
}

