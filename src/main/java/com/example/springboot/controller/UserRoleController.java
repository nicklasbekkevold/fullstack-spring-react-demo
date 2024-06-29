package com.example.springboot.controller;


import com.example.springboot.exception.EntityNotFoundException;
import com.example.springboot.model.UserRole;
import com.example.springboot.repository.UserRoleRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserRoleController {

    private final UserRoleRepository repository;
    private final UserRoleModelAssembler assembler;

    UserRoleController(UserRoleRepository repository, UserRoleModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/user-roles")
    CollectionModel<EntityModel<UserRole>> getAll() {

        List<EntityModel<UserRole>> userRoles = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(userRoles, linkTo(methodOn(UserRoleController.class).getAll()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // Single item
    // tag::get-single-item[]
    @GetMapping("/user-roles/{id}")
    EntityModel<UserRole> get(@PathVariable int id) {

        UserRole userRole = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("userRole", id));

        return assembler.toModel(userRole);
    }
    // end::get-single-item[]

}
