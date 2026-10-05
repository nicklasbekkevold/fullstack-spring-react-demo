package com.example.springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity(name = "roles")
@SequenceGenerator(name = "role_id_generator", initialValue = 101, allocationSize = 100)
public class Role extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_generator")
    private int id;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    private int version = 1;
    private String name;

    public Role(int version, String name) {
        this.version = version;
        this.name = name;
    }

    public Role(String name) {
        this(1, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return this.id == role.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Role{id=%d, version=%d, name='%s'}".formatted(id, version, name);
    }
}
