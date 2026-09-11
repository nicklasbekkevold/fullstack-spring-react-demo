package com.example.springboot.service;

import com.example.springboot.exceptions.ResourceNotFoundException;
import com.example.springboot.model.Role;
import com.example.springboot.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> findAll() {
        return repository.findAll();
    }

    public Role findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID %d could not be found".formatted(id)));
    }

}
