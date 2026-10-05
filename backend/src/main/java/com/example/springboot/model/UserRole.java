package com.example.springboot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity(name = "user_roles")
@SequenceGenerator(name = "user_role_id_generator", initialValue = 1001, allocationSize = 100)
public class UserRole extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_id_generator")
    private int id;

    private int version = 1;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unitId", nullable = false)
    private Unit unit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;

    private Instant validFrom;

    private Instant validTo;

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

    public void setVersion(int version) {
        this.version = version;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom == null ? Instant.now() : validFrom;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole userRole)) return false;
        return this.id == userRole.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserRole{id=%d, version=%d, userId=%s, unitId=%s, roleId=%s, validFrom=%s, validTo=%s}".formatted(id, version, user.getId(), unit.getId(), role.getId(), validFrom, validTo);
    }
}
