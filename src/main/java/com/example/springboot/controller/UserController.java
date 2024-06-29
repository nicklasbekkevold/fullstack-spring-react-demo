package com.example.springboot.controller;


import com.example.springboot.exception.EntityNotFoundException;
import com.example.springboot.exception.VersionMismatchException;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
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

@RestController
public class UserController {

    private final UserRepository repository;
    private final UserModelAssembler assembler;

    UserController(UserRepository repository, UserModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> getAll() {

        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Single item
    // tag::get-single-item[]
    @GetMapping("/users/{id}")
    EntityModel<User> get(@PathVariable int id) {

        User user = repository.findById(id) //
                .orElseThrow(() -> new EntityNotFoundException("user", id));

        return assembler.toModel(user);
    }
    // end::get-single-item[]

    @PutMapping("/users/{id}")
    ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable int id, @RequestParam int version) {
        User updatedUser = repository.findById(id)
                .map(user -> {
                    if (version != user.getVersion()) {
                        throw new VersionMismatchException(version, user.getVersion());
                    }
                    user.setName(newUser.getName());
                    user.setVersion(newUser.getVersion());
                    return repository.save(user);
                })
                .orElseThrow(() -> new EntityNotFoundException("user", id));

        EntityModel<User> entityModel = assembler.toModel(updatedUser);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable int id, @RequestParam int version) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user", id));

        if (version != user.getVersion()) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail("Specified version %d does not match current version %d".formatted(version, user.getVersion())));
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
