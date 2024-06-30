package com.example.springboot.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name="unit_id_generator", initialValue=11, allocationSize=100)
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="unit_id_generator")
    private int id;

    @OneToMany(mappedBy = "unit")
    private List<UserRole> userRoles;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return getId() == unit.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Unit{id=%d, version=%d, name='%s'}".formatted(id, version, name);
    }
}
