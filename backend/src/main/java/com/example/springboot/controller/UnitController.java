package com.example.springboot.controller;


import com.example.springboot.model.Unit;
import com.example.springboot.service.UnitService;
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

@Tag(name = "Units")
@RestController
@RequestMapping("/api/units")
public class UnitController {

    private final UnitService service;
    private final UnitModelAssembler assembler;

    public UnitController(UnitService service, UnitModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    public CollectionModel<EntityModel<Unit>> getAll() {
        List<EntityModel<Unit>> units = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(units, linkTo(methodOn(UnitController.class).getAll()).withSelfRel());
    }
    // end::get-aggregate-root[]

    // Single item
    // tag::get-single-item[]
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Unit>> getUnit(@PathVariable int id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }
    // end::get-single-item[]

}
