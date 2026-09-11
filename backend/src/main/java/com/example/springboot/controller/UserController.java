package com.example.springboot.controller;


import com.example.springboot.model.User;
import com.example.springboot.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final UserModelAssembler assembler;

    UserController(UserService service, UserModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping
    CollectionModel<EntityModel<User>> getAll() {
        List<EntityModel<User>> users = service.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        newUser.setVersion(1);
        EntityModel<User> entityModel = assembler.toModel(service.save(newUser));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Single item
    // tag::get-single-item[]
    @GetMapping("/{id}")
    ResponseEntity<EntityModel<User>> getUser(@PathVariable int id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }
    // end::get-single-item[]

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable int id, @RequestParam int version) {
        User existingUser = service.findById(id);

        if (version != existingUser.getVersion()) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail("Specified version %d does not match current version %d".formatted(version, existingUser.getVersion())));
        }

        existingUser.setName(newUser.getName());
        existingUser.setVersion(newUser.getVersion());
        service.save(existingUser);

        EntityModel<User> entityModel = assembler.toModel(existingUser);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable int id, @RequestParam int version) {
        User user = service.findById(id);

        if (version != user.getVersion()) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail("Specified version %d does not match current version %d".formatted(version, user.getVersion())));
        }

        if (!service.existsByIdAndUserRolesIsEmpty(id)) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail("Cannot delete user with existing user roles"));
        }

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
