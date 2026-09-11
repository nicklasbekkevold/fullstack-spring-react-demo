package com.example.springboot.service;

import com.example.springboot.exceptions.ResourceNotFoundException;
import com.example.springboot.model.UserRole;
import com.example.springboot.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserRoleService {

    private final UserRoleRepository repository;

    public UserRoleService(UserRoleRepository repository) {
        this.repository = repository;
    }

    public List<UserRole> findAll() {
        return repository.findAll();
    }

    public UserRole findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User role with ID %d could not be found".formatted(id)));
    }

    public boolean exists(int userId, int unitId, int roleId, Instant timestamp) {
        return repository.existsByUserIdAndUnitIdAndRoleIdAndValidFromBeforeAndValidToAfterOrValidFromBeforeAndValidToIsNull(userId, unitId, roleId, timestamp, timestamp, timestamp);
    }

    public List<UserRole> findValidUserRoles(int userId, int unitId, Instant timestamp) {
        return repository.findByUserIdAndUnitIdAndValidFromBeforeAndValidToAfterOrValidFromBeforeAndValidToIsNull(userId, unitId, timestamp, timestamp, timestamp);
    }

    public UserRole save(UserRole userRole) {
        return repository.save(userRole);
    }
}
