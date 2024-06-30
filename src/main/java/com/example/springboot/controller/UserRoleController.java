package com.example.springboot.controller;


import com.example.springboot.model.UserRole;
import com.example.springboot.service.UserRoleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserRoleController {

    private final UserRoleService service;
    private final UserRoleModelAssembler assembler;

    UserRoleController(UserRoleService service, UserRoleModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/user-roles")
    CollectionModel<EntityModel<UserRole>> getAll() {

        List<EntityModel<UserRole>> userRoles = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(userRoles, linkTo(methodOn(UserRoleController.class).getAll()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // Single item
    // tag::get-single-item[]
    @GetMapping("/user-roles/{id}")
    EntityModel<UserRole> get(@PathVariable int id) {
        UserRole userRole = service.findById(id);
        return assembler.toModel(userRole);
    }
    // end::get-single-item[]

    @GetMapping(value = "/user-roles", params = {"userId", "unitId", "timestamp"})
    CollectionModel<EntityModel<UserRole>> getValid(
            @RequestParam int userId,
            @RequestParam int unitId,
            @RequestParam("timestamp") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant timestamp
    ) {
        List<EntityModel<UserRole>> userRoles = service.findValidUserRoles(userId, unitId, timestamp).stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(userRoles, linkTo(methodOn(UserRoleController.class).getAll()).withSelfRel());
    }

}
