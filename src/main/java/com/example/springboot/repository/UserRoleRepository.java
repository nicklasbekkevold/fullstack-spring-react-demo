package com.example.springboot.repository;


import com.example.springboot.model.Role;
import com.example.springboot.model.Unit;
import com.example.springboot.model.User;
import com.example.springboot.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    List<UserRole> findByUser(User user);
    List<UserRole> findByUnit(Unit unit);
    List<UserRole> findByRole(Role role);

    List<UserRole> findByUserAndUnit(User user, Unit unit);
}
