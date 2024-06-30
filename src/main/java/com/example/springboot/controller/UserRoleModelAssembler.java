package com.example.springboot.controller;

import com.example.springboot.model.UserRole;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserRoleModelAssembler implements RepresentationModelAssembler<UserRole, EntityModel<UserRole>> {

    @Override
    public EntityModel<UserRole> toModel(UserRole userUserRole) {
        return EntityModel.of(userUserRole,
                linkTo(methodOn(UserRoleController.class).getUserRole(userUserRole.getId())).withSelfRel(),
                linkTo(methodOn(UserRoleController.class).getAll()).withRel("user-roles"));
    }
}