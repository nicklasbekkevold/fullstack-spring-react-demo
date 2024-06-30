package com.example.springboot.service;

import com.example.springboot.model.Role;
import com.example.springboot.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> findAll() {
        return repository.findAll();
    }

    public Optional<Role> findById(int id) {
        return repository.findById(id);
    }

    public Role save(Role user) {
        return repository.save(user);
    }

    public Role deleteById(Role user) { return repository.save(user); }
}
