package com.example.springboot.model;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name="unit_id_generator", initialValue=11, allocationSize=100)
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="unit_id_generator")
    private int id;

    private int version;
    private String name;

    public Unit() {
        this.version = 1;
    }

    public Unit(int version, String name) {
        this.version = version;
        this.name = name;
    }

    public Unit(String name) {
        this.version = 1;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Unit{id=%d, version=%d, name='%s'}".formatted(id, version, name);
    }
}
