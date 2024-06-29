package com.example.springboot.controller;


import com.example.springboot.exception.EntityNotFoundException;
import com.example.springboot.exception.VersionMismatchException;
import com.example.springboot.model.Role;
import com.example.springboot.model.Unit;
import com.example.springboot.repository.RoleRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RoleController {

    private final RoleRepository repository;

    RoleController(RoleRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/roles")
    CollectionModel<EntityModel<Role>> all() {

        List<EntityModel<Role>> roles = repository.findAll().stream()
                .map(role -> EntityModel.of(role,
                        linkTo(methodOn(RoleController.class).one(role.getId())).withSelfRel(),
                        linkTo(methodOn(RoleController.class).all()).withRel("roles")))
                .collect(Collectors.toList());

        return CollectionModel.of(roles, linkTo(methodOn(RoleController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/roles")
    Role newRole(@RequestBody Role newRole) {
        return repository.save(newRole);
    }

    // Single item
    // tag::get-single-item[]
    @GetMapping("/roles/{id}")
    EntityModel<Role> one(@PathVariable int id) {

        Role role = repository.findById(id) //
                .orElseThrow(() -> new EntityNotFoundException("role", id));

        return EntityModel.of(role, //
                linkTo(methodOn(RoleController.class).one(id)).withSelfRel(),
                linkTo(methodOn(RoleController.class).all()).withRel("roles"));
    }
    // end::get-single-item[]
}
