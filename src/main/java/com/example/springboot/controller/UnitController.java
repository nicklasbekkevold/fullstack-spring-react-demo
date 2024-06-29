package com.example.springboot.controller;


import com.example.springboot.exception.EntityNotFoundException;
import com.example.springboot.model.Unit;
import com.example.springboot.repository.UnitRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UnitController {

    private final UnitRepository repository;

    UnitController(UnitRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/units")
    CollectionModel<EntityModel<Unit>> all() {

        List<EntityModel<Unit>> units = repository.findAll().stream()
                .map(unit -> EntityModel.of(unit,
                        linkTo(methodOn(UnitController.class).one(unit.getId())).withSelfRel(),
                        linkTo(methodOn(UnitController.class).all()).withRel("units")))
                .collect(Collectors.toList());

        return CollectionModel.of(units, linkTo(methodOn(UnitController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // Single item
    // tag::get-single-item[]
    @GetMapping("/units/{id}")
    EntityModel<Unit> one(@PathVariable int id) {

        Unit unit = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("unit", id));

        return EntityModel.of(unit, //
                linkTo(methodOn(UnitController.class).one(id)).withSelfRel(),
                linkTo(methodOn(UnitController.class).all()).withRel("units"));
    }
    // end::get-single-item[]

}
