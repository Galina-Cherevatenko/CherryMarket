package com.example.cherrymarket1.mappers;

import com.example.cherrymarket1.dto.ItemInOrderDTO;
import com.example.cherrymarket1.entities.ItemInOrder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class ItemInOrderMapper {

    private ModelMapper modelMapper;
    public ItemInOrderMapper() {
        this.modelMapper = new ModelMapper();
    }

    public ItemInOrder convertToItemInOrder (ItemInOrderDTO itemInOrderDTO) {
        return Objects.isNull(itemInOrderDTO) ? null: modelMapper.map(itemInOrderDTO, ItemInOrder.class);
    }

    public ItemInOrderDTO convertToItemInOrderDTO (ItemInOrder itemInOrder) {
        return Objects.isNull(itemInOrder) ? null: modelMapper.map(itemInOrder, ItemInOrderDTO.class);
    }
}
