package com.example.springboot.repository;


import com.example.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByIdAndUserRolesIsEmpty(int id);

    @Modifying
    @Transactional
    void deleteById(int id);

}
