package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;
@Repository
public class ItemStorageImpl implements ItemStorage {
    private ItemMapper itemMapper;
    private final Map<Integer, Item> itemsMap = new HashMap<>();
    private int id = 1;

    @Override
    public Item create(Item item) {
        item.setId(id);
        itemsMap.put(item.getId(), item);
        ++id;
        return item;
    }

    @Override
    public Item update(Item item, int id) {
            itemsMap.put(id, item);
            return item;
    }

    @Override
    public Item getItemById(int id) {
            return itemsMap.get(id);
    }

    @Override
    public Collection<Item> getAllItems() {
        return itemsMap.values();
    }

    @Override
    public List<Item> searchItem(String query) {
        String lowerCaseQuery = query.toLowerCase();

        return itemsMap.values().stream()
                .filter(item -> item.getAvailable() &&
                        (item.getName().toLowerCase().contains(lowerCaseQuery) ||
                                item.getDescription().toLowerCase().contains(lowerCaseQuery)))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Item> getAllItemsByUserId(Integer userId) {
        return itemsMap.values().stream()
                .filter(item -> item.getOwner() != null && item.getOwner().getId() == userId)
                .collect(Collectors.toSet());
    }
}
