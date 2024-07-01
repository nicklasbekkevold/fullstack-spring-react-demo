package com.example.springboot.controller;


import com.example.springboot.model.User;
import com.example.springboot.service.UserService;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    ResponseEntity<?> getUser(@PathVariable int id) {
        Optional<User> user = service.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Not found")
                            .withDetail("Could not find user with id %d".formatted(id)));

        }
        return ResponseEntity.ok(assembler.toModel(user.get()));
    }
    // end::get-single-item[]

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable int id, @RequestParam int version) {
        Optional<User> existingUser = service.findById(id);
        if (existingUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Not found")
                            .withDetail("Could not find user with id %d".formatted(id)));

        }

        if (version != existingUser.get().getVersion()) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail("Specified version %d does not match current version %d".formatted(version, existingUser.get().getVersion())));
        }

        existingUser.get().setName(newUser.getName());
        existingUser.get().setVersion(newUser.getVersion());
        service.save(existingUser.get());

        EntityModel<User> entityModel = assembler.toModel(existingUser.get());
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable int id, @RequestParam int version) {
        Optional<User> user = service.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Not found")
                            .withDetail("Could not find user with id %d".formatted(id)));

        }

        if (version != user.get().getVersion()) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail("Specified version %d does not match current version %d".formatted(version, user.get().getVersion())));
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
