package com.example.springboot.service;

import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(int id) {
        return repository.findById(id);
    }

    public boolean existsByIdAndUserRolesIsEmpty(int id) {
        return repository.existsByIdAndUserRolesIsEmpty(id);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void deleteById(int id) { repository.deleteById(id); }
}
