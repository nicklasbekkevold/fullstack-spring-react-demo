package com.example.springboot.controller;

import com.example.springboot.model.Unit;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UnitModelAssembler implements RepresentationModelAssembler<Unit, EntityModel<Unit>> {

    @Override
    public EntityModel<Unit> toModel(Unit unit) {
        return EntityModel.of(unit,
                linkTo(methodOn(UnitController.class).getUnit(unit.getId())).withSelfRel(),
                linkTo(methodOn(UnitController.class).getAll()).withRel("units"));
    }
}