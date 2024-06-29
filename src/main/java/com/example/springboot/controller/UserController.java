package com.example.springboot.controller;


import com.example.springboot.exception.EntityNotFoundException;
import com.example.springboot.exception.VersionMismatchException;
import com.example.springboot.model.User;
import com.example.springboot.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> all() {

        List<EntityModel<User>> users = repository.findAll().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).one(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserController.class).all()).withRel("users")))
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item
    // tag::get-single-item[]
    @GetMapping("/users/{id}")
    EntityModel<User> one(@PathVariable int id) {

        User user = repository.findById(id) //
                .orElseThrow(() -> new EntityNotFoundException("user", id));

        return EntityModel.of(user, //
                linkTo(methodOn(UserController.class).one(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).all()).withRel("users"));
    }
    // end::get-single-item[]

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable int id, @RequestParam int version) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user", id));

        if (version != user.getVersion()) {
            throw new VersionMismatchException(version, user.getVersion());
        }

        user.setName(newUser.getName());
        user.setVersion(newUser.getVersion());
        return repository.save(user);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable int id, @RequestParam int version) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user", id));

        if (version != user.getVersion()) {
            throw new VersionMismatchException(version, user.getVersion());
        }

        repository.deleteById(id);
    }
}
