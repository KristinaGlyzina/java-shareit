package ru.practicum.shareit.exception;

public class ExistException extends RuntimeException {

    public ExistException(String error) {
        super(error);
    }
}