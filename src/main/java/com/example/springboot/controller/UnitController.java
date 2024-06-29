package com.example.springboot.controller;


import com.example.springboot.exception.EntityNotFoundException;
import com.example.springboot.model.Unit;
import com.example.springboot.model.User;
import com.example.springboot.model.UserRole;
import com.example.springboot.repository.UnitRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.repository.UserRoleRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UnitController {

    private final UnitRepository repository;
    private final UnitModelAssembler assembler;

    public UnitController(UnitRepository repository, UnitModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/units")
    public CollectionModel<EntityModel<Unit>> getAll() {

        List<EntityModel<Unit>> units = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(units, linkTo(methodOn(UnitController.class).getAll()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // Single item
    // tag::get-single-item[]
    @GetMapping("/units/{id}")
    public EntityModel<Unit> get(@PathVariable int id) {

        Unit unit = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("unit", id));

        return assembler.toModel(unit);
    }
    // end::get-single-item[]

}
