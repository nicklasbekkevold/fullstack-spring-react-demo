package com.example.springboot.repository;


import com.example.springboot.model.Role;
import com.example.springboot.model.Unit;
import com.example.springboot.model.User;
import com.example.springboot.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    List<UserRole> findByUserIdAndUnitIdAndValidFromBeforeAndValidToAfter(int userId, int unit, Instant validFrom, Instant validTo);
    List<UserRole> findByUserIdAndUnitIdAndValidFromBeforeAndValidToIsNull(int userId, int unit, Instant validFrom);
}
