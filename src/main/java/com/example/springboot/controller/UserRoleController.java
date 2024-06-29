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

    UserRoleController(UserRoleRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/user-roles")
    CollectionModel<EntityModel<UserRole>> all() {

        List<EntityModel<UserRole>> userRoles = repository.findAll().stream()
                .map(userRole -> EntityModel.of(userRole,
                        linkTo(methodOn(UserRoleController.class).one(userRole.getId())).withSelfRel(),
                        linkTo(methodOn(UserRoleController.class).all()).withRel("userRoles")))
                .collect(Collectors.toList());

        return CollectionModel.of(userRoles, linkTo(methodOn(UserRoleController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // Single item
    // tag::get-single-item[]
    @GetMapping("/user-roles/{id}")
    EntityModel<UserRole> one(@PathVariable int id) {

        UserRole userRole = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("userRole", id));

        return EntityModel.of(userRole,
                linkTo(methodOn(UserRoleController.class).one(id)).withSelfRel(),
                linkTo(methodOn(UserRoleController.class).all()).withRel("userRoles"));
    }
    // end::get-single-item[]

}
