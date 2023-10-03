package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        if (userRepository.allEmails().contains(user.getEmail())) {
            throw new ValidationException("User with email " + user.getEmail() + " already exists");
        }
        userRepository.create(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public User update(int id, UserDto userDto) {
        UserDto existingUser = getUserById(id);

        if (existingUser == null) {
            throw new ValidationException("User with ID " + id + " is not found and cannot be updated");
        }

        String updatedEmail = userDto.getEmail();
        String updatedName = userDto.getName();

        if (updatedEmail != null && !updatedEmail.isBlank() && !updatedEmail.equals(existingUser.getEmail())) {
            if (userRepository.allEmails().contains(updatedEmail)) {
                throw new ValidationException("User with email " + updatedEmail + " already exists");
            }
            userRepository.allEmails().remove(existingUser.getEmail());
            existingUser.setEmail(updatedEmail);
        }

        if (updatedName != null && !updatedName.isBlank()) {
            existingUser.setName(updatedName);
        }
        return userRepository.update(UserMapper.toUser(existingUser), id);
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }
        return UserMapper.toUserDto(user);
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        Collection<User> users = userRepository.getAllUsers();
        Collection<UserDto> userDtos = users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }
}
