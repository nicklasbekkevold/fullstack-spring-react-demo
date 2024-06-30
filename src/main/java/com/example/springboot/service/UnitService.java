package com.example.springboot.service;

import com.example.springboot.model.Unit;
import com.example.springboot.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

    private final UnitRepository repository;

    @Autowired
    public UnitService(UnitRepository repository) {
        this.repository = repository;
    }

    public List<Unit> findAll() {
        return repository.findAll();
    }

    public Optional<Unit> findById(int id) {
        return repository.findById(id);
    }

    public Unit save(Unit user) {
        return repository.save(user);
    }

    public Unit deleteById(Unit user) { return repository.save(user); }
}
