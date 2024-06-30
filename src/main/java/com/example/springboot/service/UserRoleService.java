package com.example.springboot.service;

import com.example.springboot.dto.UserRoleCreationDto;
import com.example.springboot.exception.EntityNotFoundException;
import com.example.springboot.model.UserRole;
import com.example.springboot.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserRoleService {

    private final UserRoleRepository repository;

    @Autowired
    public UserRoleService(UserRoleRepository repository) {
        this.repository = repository;
    }

    public List<UserRole> findAll() {
        return repository.findAll();
    }

    public UserRole findById(int id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("userRole", id)
        );
    }

    public boolean exists(int userId, int unitId, int roleId) {
        return repository.existsByUserIdAndUnitIdAndRoleId(userId, unitId, roleId);
    }

    public List<UserRole> findValidUserRoles(int userId, int unitId, Instant timestamp) {
        return repository.findByUserIdAndUnitIdAndValidFromBeforeAndValidToAfterOrValidFromBeforeAndValidToIsNull(userId, unitId, timestamp, timestamp, timestamp);
    }

    public UserRole save(UserRole userRole) {
        return repository.save(userRole);
    }
}
