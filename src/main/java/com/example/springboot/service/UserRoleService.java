package com.example.springboot.service;

import com.example.springboot.model.UserRole;
import com.example.springboot.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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

    public Optional<UserRole> findById(int id) {
        return repository.findById(id);
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
