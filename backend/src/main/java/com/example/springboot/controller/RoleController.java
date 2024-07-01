package com.example.springboot.controller;


import com.example.springboot.model.Role;
import com.example.springboot.service.RoleService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        Optional<Role> role = service.findById(id);

        if (role.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Not found")
                            .withDetail("Could not find role with id %d".formatted(id)));

        }
        return ResponseEntity.ok(assembler.toModel(role.get()));
    }
    // end::get-single-item[]
}
