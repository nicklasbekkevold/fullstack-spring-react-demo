package com.example.springboot.service;

import com.example.springboot.exceptions.ResourceNotFoundException;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID %d could not found".formatted(id)));
    }

    public boolean existsByIdAndUserRolesIsEmpty(int id) {
        return repository.existsByIdAndUserRolesIsEmpty(id);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
