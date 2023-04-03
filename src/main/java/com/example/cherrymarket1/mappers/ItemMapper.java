package com.example.cherrymarket1.mappers;


import com.example.cherrymarket1.dto.ItemDTO;
import com.example.cherrymarket1.entities.Item;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ItemMapper {

    private ModelMapper modelMapper;
    public ItemMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Item convertToItem (ItemDTO itemDTO) {
        return Objects.isNull(itemDTO) ? null: modelMapper.map(itemDTO, Item.class);
    }

    public ItemDTO convertToItemDTO (Item item) {
        return Objects.isNull(item) ? null: modelMapper.map(item, ItemDTO.class);
    }
}

