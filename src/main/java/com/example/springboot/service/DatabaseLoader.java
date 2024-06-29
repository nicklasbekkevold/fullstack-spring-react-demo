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
        log.info("Preloading {}", userRepository.save(new User(1, "Alice")));
        log.info("Preloading {}", userRepository.save(new User(2, "Bob")));
        log.info("Preloading {}", userRepository.save(new User(1, "Eve")));

        // Unit
        log.info("Preloading {}", unitRepository.save(new Unit(2, "Kreftregisteret")));
        log.info("Preloading {}", unitRepository.save(new Unit(1, "Akershus universitetssykehus HF")));
        log.info("Preloading {}", unitRepository.save(new Unit(2, "Sørlandet sykehus HF")));
        log.info("Preloading {}", unitRepository.save(new Unit(2, "Vestre Viken HF")));

        // Role
        log.info("Preloading {}", roleRepository.save(new Role(1, "User administration")));
        log.info("Preloading {}", roleRepository.save(new Role(2, "Endoscopist administration")));
        log.info("Preloading {}", roleRepository.save(new Role(1, "Report colonoscopy capacity")));
        log.info("Preloading {}", roleRepository.save(new Role(2, "Send invitations")));
        log.info("Preloading {}", roleRepository.save(new Role(1, "View statistics")));

        // User role
        log.info("Preloading {}", userRoleRepository.save(new UserRole(
                1,
                userRepository.findById(1).orElseThrow(),
                unitRepository.findById(11).orElseThrow(),
                roleRepository.findById(101).orElseThrow(),
                Instant.parse("2019-01-02T00:00:00+02:00"),
                Instant.parse("2019-12-31T23:59:59+02:00")
                ))
        );
        log.info("Preloading {}", userRoleRepository.save(
                new UserRole(
                        2,
                        userRepository.findById(1).orElseThrow(),
                        unitRepository.findById(11).orElseThrow(),
                        roleRepository.findById(104).orElseThrow(),
                        Instant.parse("2019-01-02T00:00:00+02:00"),
                        Instant.parse("2019-12-31T23:59:59+02:00")
                ))
        );
        log.info("Preloading {}", userRoleRepository.save(
                new UserRole(
                        1,
                        userRepository.findById(1).orElseThrow(),
                        unitRepository.findById(11).orElseThrow(),
                        roleRepository.findById(105).orElseThrow(),
                        Instant.parse("2019-06-11T00:00:00+02:00"),
                        Instant.parse("2019-12-31T23:59:59+02:00")
                ))
        );
        log.info("Preloading {}", userRoleRepository.save(
                new UserRole(
                        2,
                        userRepository.findById(2).orElseThrow(),
                        unitRepository.findById(12).orElseThrow(),
                        roleRepository.findById(101).orElseThrow(),
                        Instant.parse("2020-01-28T00:00:00+02:00"),
                        Instant.parse("2019-12-31T23:59:59+02:00")
                ))
        );
        log.info("Preloading {}", userRoleRepository.save(
                new UserRole(
                        1,
                        userRepository.findById(2).orElseThrow(),
                        unitRepository.findById(12).orElseThrow(),
                        roleRepository.findById(105).orElseThrow(),
                        Instant.parse("2020-01-28T00:00:00+02:00"),
                        null
                ))
        );
        log.info("Preloading {}", userRoleRepository.save(
                new UserRole(
                        1,
                        userRepository.findById(2).orElseThrow(),
                        unitRepository.findById(14).orElseThrow(),
                        roleRepository.findById(101).orElseThrow(),
                        Instant.parse("2020-01-28T00:00:00+02:00"),
                        null
                ))
        );
        log.info("Preloading {}", userRoleRepository.save(
                new UserRole(
                        1,
                        userRepository.findById(2).orElseThrow(),
                        unitRepository.findById(14).orElseThrow(),
                        roleRepository.findById(102).orElseThrow(),
                        Instant.parse("2020-01-28T00:00:00+02:00"),
                        null
                ))
        );
        log.info("Preloading {}", userRoleRepository.save(
                new UserRole(
                        1,
                        userRepository.findById(1).orElseThrow(),
                        unitRepository.findById(11).orElseThrow(),
                        roleRepository.findById(101).orElseThrow(),
                        Instant.parse("2020-02-01T07:00:00+02:00"),
                        null
                ))
        );
        log.info("Preloading {}", userRoleRepository.save(
                new UserRole(
                        1,
                        userRepository.findById(1).orElseThrow(),
                        unitRepository.findById(11).orElseThrow(),
                        roleRepository.findById(104).orElseThrow(),
                        Instant.parse("2020-02-01T07:00:00+02:00"),
                        null
                ))
        );

    }
}
