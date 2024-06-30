package com.example.springboot.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@SequenceGenerator(name="role_id_generator", initialValue=101, allocationSize=100)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="role_id_generator")
    private int id;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    private int version;
    private String name;

    public Role() {
        this.version = 1;
    }

    public Role(int version, String name) {
        this.version = version;
        this.name = name;
    }

    public Role(String name) {
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
        return "Role{id=%d, version=%d, name='%s'}".formatted(id, version, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return getId() == role.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
