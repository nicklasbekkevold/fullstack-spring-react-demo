package com.example.springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity(name = "units")
@SequenceGenerator(name = "unit_id_generator", initialValue = 11, allocationSize = 100)
public class Unit extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_id_generator")
    private int id;

    @OneToMany(mappedBy = "unit")
    private List<UserRole> userRoles;

    private int version = 1;
    private String name;

    public Unit(int version, String name) {
        this.version = version;
        this.name = name;
    }

    public Unit(String name) {
        this(1, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit unit)) return false;
        return this.id == unit.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Unit{id=%d, version=%d, name='%s'}".formatted(id, version, name);
    }
}
