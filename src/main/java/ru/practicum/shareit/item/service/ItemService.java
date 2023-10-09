package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Set;

public interface ItemService {
    ItemDto create(int id, ItemDto itemDto);

    ItemDto update(int userId, ItemDto itemDto, int itemId);

    ItemDto getItemById(int id);

    Set<ItemDto> getAllItems(int userId);

    List<Item> searchItem(String text);
}
