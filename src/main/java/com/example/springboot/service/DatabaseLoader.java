package com.example.springboot.service;

import com.example.springboot.model.Role;
import com.example.springboot.model.Unit;
import com.example.springboot.model.User;
import com.example.springboot.model.UserRole;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UnitRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
class DatabaseLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    private DatabaseLoader(UserRepository userRepository, UnitRepository unitRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.unitRepository = unitRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) {
        // User
        List<User> users = List.of(
                new User(1, "Alice"),
                new User(2, "Bob"),
                new User(1, "Eve")
        );
        log.info("Preloading users: {}", userRepository.saveAll(users));

        // Unit
        List<Unit> units = List.of(
            new Unit(2, "Kreftregisteret"),
            new Unit(1, "Akershus universitetssykehus HF"),
            new Unit(2, "Sørlandet sykehus HF"),
            new Unit(2, "Vestre Viken HF")
        );
        log.info("Preloading units: {}", unitRepository.saveAll(units));

        // Role
        List<Role> roles = List.of(
            new Role(1, "User administration"),
            new Role(2, "Endoscopist administration"),
            new Role(1, "Report colonoscopy capacity"),
            new Role(2, "Send invitations"),
            new Role(1, "View statistics")
        );
        log.info("Preloading roles: {}", roleRepository.saveAll(roles));

        // User role
        List<UserRole> userRoles = List.of(
            new UserRole(
                1,
                users.get(0),
                units.get(0),
                roles.get(1),
                Instant.parse("2019-01-02T00:00:00+02:00"),
                Instant.parse("2019-12-31T23:59:59+02:00")
                ),
                new UserRole(
                        2,
                        users.get(0),
                        units.get(0),
                        roles.get(3),
                        Instant.parse("2019-01-02T00:00:00+02:00"),
                        Instant.parse("2019-12-31T23:59:59+02:00")
                ),
                new UserRole(
                        1,
                        users.get(0),
                        units.get(0),
                        roles.get(4),
                        Instant.parse("2019-06-11T00:00:00+02:00"),
                        Instant.parse("2019-12-31T23:59:59+02:00")
                ),
                new UserRole(
                        2,
                        users.get(1),
                        units.get(1),
                        roles.get(0),
                        Instant.parse("2020-01-28T00:00:00+02:00"),
                        Instant.parse("2019-12-31T23:59:59+02:00")
                ),
                new UserRole(
                        1,
                        users.get(1),
                        units.get(1),
                        roles.get(4),
                        Instant.parse("2020-01-28T00:00:00+02:00"),
                        null
                ),
                new UserRole(
                        1,
                        users.get(1),
                        units.get(3),
                        roles.get(0),
                        Instant.parse("2020-01-28T00:00:00+02:00"),
                        null
                ),
                new UserRole(
                        1,
                        users.get(1),
                        units.get(3),
                        roles.get(1),
                        Instant.parse("2020-01-28T00:00:00+02:00"),
                        null
                ),
                new UserRole(
                        1,
                        users.get(0),
                        units.get(0),
                        roles.get(0),
                        Instant.parse("2020-02-01T07:00:00+02:00"),
                        null
                ),
                new UserRole(
                        1,
                        users.get(0),
                        units.get(0),
                        roles.get(3),
                        Instant.parse("2020-02-01T07:00:00+02:00"),
                        null
                )
        );
        log.info("Preloading user roles: {}", userRoleRepository.saveAll(userRoles));

    }
}
