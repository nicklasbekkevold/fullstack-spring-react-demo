package com.example.springboot.dto;

import com.example.springboot.model.Role;
import com.example.springboot.model.Unit;
import com.example.springboot.model.User;
import com.example.springboot.model.UserRole;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UnitRepository;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class UserRoleCreationMapper {

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserRoleCreationMapper(UserRepository userRepository, UnitRepository unitRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.unitRepository = unitRepository;
        this.roleRepository = roleRepository;
    }

    public UserRoleCreationDto toDto(UserRole userRole) {
        return new UserRoleCreationDto(
                userRole.getUser().getId(),
                userRole.getUnit().getId(),
                userRole.getRole().getId(),
                userRole.getValidFrom(),
                userRole.getValidTo()
        );
    }

    public UserRole toUserRole(UserRoleCreationDto userRoleDto) throws NoSuchElementException {
        User user = userRepository.findById(userRoleDto.getUserId()).orElseThrow();
        Unit unit = unitRepository.findById(userRoleDto.getUnitId()).orElseThrow();
        Role role = roleRepository.findById(userRoleDto.getRoleId()).orElseThrow();

        return new UserRole(
                user,
                unit,
                role,
                userRoleDto.getValidFrom(),
                userRoleDto.getValidTo()
        );
    }

}
