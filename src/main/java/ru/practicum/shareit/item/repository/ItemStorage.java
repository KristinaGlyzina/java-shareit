package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ItemStorage {
    Item create(Item item);

    Item update(Item item, int id);

    Item getItemById(int id);

    Collection<Item> getAllItems();

    List<Item> searchItem(String query);

    Set<Item> getAllItemsByUserId(Integer userId);
}
