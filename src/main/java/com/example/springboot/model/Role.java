package com.example.springboot.model;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name="role_id_generator", initialValue=101, allocationSize=100)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="role_id_generator")
    private int id;

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
}
