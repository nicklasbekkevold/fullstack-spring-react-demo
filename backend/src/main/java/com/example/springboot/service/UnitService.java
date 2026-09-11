package com.example.springboot.service;

import com.example.springboot.exceptions.ResourceNotFoundException;
import com.example.springboot.model.Unit;
import com.example.springboot.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    private final UnitRepository repository;

    public UnitService(UnitRepository repository) {
        this.repository = repository;
    }

    public List<Unit> findAll() {
        return repository.findAll();
    }

    public Unit findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit with ID %d could not be found".formatted(id)));
    }
}
