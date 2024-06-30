package com.example.springboot.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@SequenceGenerator(name="user_role_id_generator", initialValue=1001, allocationSize=100)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_role_id_generator")
    private int id;

    private int version = 1;

    @ManyToOne(optional = false)
    @JoinColumn(name="userId", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name="unitId", nullable = false)
    private Unit unit;

    @ManyToOne(optional = false)
    @JoinColumn(name="roleId", nullable = false)
    private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant validFrom;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant validTo;

    public UserRole() { }

    public UserRole(int version, User user, Unit unit, Role role, Instant validFrom, Instant validTo) {
        this.version = version;
        this.user = user;
        this.unit = unit;
        this.role = role;
        this.validFrom = validFrom == null ? Instant.now() : validFrom;
        this.validTo = validTo;
    }

    public UserRole(User user, Unit unit, Role role, Instant validFrom, Instant validTo) {
        this(1, user, unit, role, validFrom, validTo);
    }


    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public User getUser() {
        return user;
    }

    public Unit getUnit() {
        return unit;
    }

    public Role getRole() {
        return role;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return getId() == userRole.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserRole{id=%d, version=%d, userId=%s, unitId=%s, roleId=%s, validFrom=%s, validTo=%s}".formatted(id, version, user.getId(), unit.getId(), role.getId(), validFrom, validTo);
    }
}
