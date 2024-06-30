package com.example.springboot.repository;


import com.example.springboot.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    List<UserRole> findByUserIdAndUnitIdAndRoleId(int userId, int unitId, int roleId);

    List<UserRole> findByUserIdAndUnitIdAndValidFromBeforeAndValidToAfterOrValidFromBeforeAndValidToIsNull(
            int userId,
            int unitId,
            Instant validFrom,
            Instant validFromRepeat,
            Instant validTo
    );

}
