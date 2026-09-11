package com.example.springboot.controller;


import com.example.springboot.model.Role;
import com.example.springboot.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Roles")
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService service;
    private final RoleModelAssembler assembler;

    public RoleController(RoleService service, RoleModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    public CollectionModel<EntityModel<Role>> getAll() {
        List<EntityModel<Role>> roles = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(roles, linkTo(methodOn(RoleController.class).getAll()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // Single item
    // tag::get-single-item[]
    @GetMapping("/{id}")
    public ResponseEntity<?> getRole(@PathVariable int id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }
    // end::get-single-item[]
}
