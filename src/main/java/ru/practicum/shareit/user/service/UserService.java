package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserService {

    UserDto getUserById(int id);

    Collection<UserDto> getAllUsers();

    UserDto create(UserDto userDto);

    User update(int id, UserDto userDto);

    void delete(int id);

}