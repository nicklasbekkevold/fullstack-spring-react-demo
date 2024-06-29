package com.example.springboot.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity, int id) {
        super("Could not find %s with id %d".formatted(entity, id));
    }
}
