package com.example.springboot.controller;


import com.example.springboot.exception.EntityNotFoundException;
import com.example.springboot.model.Role;
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
    private final RoleModelAssembler assembler;

    RoleController(RoleRepository repository, RoleModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/roles")
    CollectionModel<EntityModel<Role>> getAll() {

        List<EntityModel<Role>> roles = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(roles, linkTo(methodOn(RoleController.class).getAll()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // Single item
    // tag::get-single-item[]
    @GetMapping("/roles/{id}")
    EntityModel<Role> get(@PathVariable int id) {

        Role role = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("role", id));

        return EntityModel.of(role,
                linkTo(methodOn(RoleController.class).get(id)).withSelfRel(),
                linkTo(methodOn(RoleController.class).getAll()).withRel("roles"));
    }
    // end::get-single-item[]
}
