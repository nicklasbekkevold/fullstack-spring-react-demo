package com.example.springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity(name = "users")
@SequenceGenerator(name = "user_id_generator", allocationSize = 100)
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    private int id;

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles;

    private int version = 1;
    private String name;

    public User(int version, String name) {
        this.version = version;
        this.name = name;
    }

    public User(String name) {
        this(1, name);
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return this.id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{id=%d, version=%d, name='%s'}".formatted(id, version, name);
    }
}
