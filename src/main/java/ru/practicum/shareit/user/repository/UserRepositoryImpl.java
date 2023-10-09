package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final Map<Integer, User> usersMap = new HashMap<>();
    private final Set<String> emails = new HashSet<>();
    private int id = 1;

    @Override
    public User create(User user) {
        user.setId(id);
        emails.add(user.getEmail());
        usersMap.put(user.getId(), user);
        ++id;
        return user;
    }

    @Override
    public User update(User user, int id) {
        usersMap.put(id, user);
        return user;
    }

    @Override
    public User getUserById(int id) {
        return usersMap.get(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        return usersMap.values();
    }

    @Override
    public void delete(int id) {
        emails.remove(getUserById(id).getEmail());
        usersMap.remove(id);
    }

    @Override
    public Set<String> allEmails() {
        return emails;
    }
}