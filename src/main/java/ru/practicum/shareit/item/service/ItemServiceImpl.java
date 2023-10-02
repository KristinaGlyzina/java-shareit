package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemStorage itemStorage;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private ItemMapper itemMapper;

    public ItemServiceImpl() {
    }


    @Override
    public ItemDto create(int userId, ItemDto itemDto) {
        if (itemDto.getDescription() == null || itemDto.getName().isBlank()) {
            throw new ValidationException("name is empty");
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new ValidationException("description is empty");
        }
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("available is empty");
        }
        UserDto userDto = userService.getUserById(userId);
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(UserMapper.toUser(userDto));
        return ItemMapper.toItemDto(itemStorage.create(item));
    }


    @Override
    public ItemDto update(int userId, ItemDto itemDto, int itemId) {
        Item existingItem = itemStorage.getItemById(itemId);
        if (existingItem == null) {
            throw new NotFoundException("Item not found with ID: " + itemId);
        }

        if (existingItem.getOwner().getId() != userId) {
            throw new NotFoundException("The user is not authorized to update this item");
        }

        if (itemDto.getName() != null) {
            existingItem.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            existingItem.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            existingItem.setAvailable(itemDto.getAvailable());
        }

        Item updatedItem = itemStorage.update(existingItem, itemId);

        return ItemMapper.toItemDto(updatedItem);
    }


    @Override
    public ItemDto getItemById(int id) {
        Item item = itemStorage.getItemById(id);
        if (item == null) {
            throw new NotFoundException("Item not found with ID: " + id);
        }
        return ItemMapper.toItemDto(item);
    }

    @Override
    public Set<ItemDto> getAllItems(int userId) {
        UserDto userDto = userService.getUserById(userId);
        if (userDto == null) {
            throw new NotFoundException("User not found with ID: " + userId);
        }
        Set<Item> userItems = itemStorage.getAllItemsByUserId(userId);

        return userItems.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Item> searchItem(String query) {
        if (query.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(itemStorage.searchItem(query));
    }
}