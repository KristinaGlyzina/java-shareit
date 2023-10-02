package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Set;


public interface UserRepository {

    User getUserById(int id);

    Collection<User> getAllUsers();

    User create(User user);

    User update(User user, int id);

    void delete(int id);

    Set<String> allEmails();
}