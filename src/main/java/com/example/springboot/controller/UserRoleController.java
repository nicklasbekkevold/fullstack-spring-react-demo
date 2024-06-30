package com.example.springboot.controller;


import com.example.springboot.dto.UserRoleCreationDto;
import com.example.springboot.dto.UserRoleCreationMapper;
import com.example.springboot.model.User;
import com.example.springboot.model.UserRole;
import com.example.springboot.service.UserRoleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserRoleController {

    private final UserRoleService service;
    private final UserRoleCreationMapper mapper;
    private final UserRoleModelAssembler assembler;

    UserRoleController(UserRoleService service, UserRoleCreationMapper mapper, UserRoleModelAssembler assembler) {
        this.service = service;
        this.mapper = mapper;
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

    @PostMapping("/user-roles")
    public ResponseEntity<?> createUserRole(@RequestBody UserRoleCreationDto userRoleDto) {
        if (service.exists(userRoleDto.getUserId(), userRoleDto.getUnitId(), userRoleDto.getRoleId())) {
            System.out.println(service.exists(userRoleDto.getUserId(), userRoleDto.getUnitId(), userRoleDto.getRoleId()));
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail("User role with specified userId, unitId, and roleId already exists."));
        }

        if (userRoleDto.getValidTo() != null && userRoleDto.getValidTo().isBefore(userRoleDto.getValidFrom())) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail("validTo is before validFrom"));
        }

        UserRole newUserRole = mapper.toUserRole(userRoleDto);
        EntityModel<UserRole> entityModel = assembler.toModel(service.save(newUserRole));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

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
