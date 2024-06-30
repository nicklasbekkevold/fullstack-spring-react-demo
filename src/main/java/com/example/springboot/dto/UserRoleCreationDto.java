package com.example.springboot.dto;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

public class UserRoleCreationDto {

    private int userId;
    private int unitId;
    private int roleId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant validFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant validTo;

    public UserRoleCreationDto() { }

    public UserRoleCreationDto(int userId, int unitId, int roleId, Instant validFrom, Instant validTo) {
        this.userId = userId;
        this.unitId = unitId;
        this.roleId = roleId;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }


    public int getUserId() {
        return userId;
    }

    public int getUnitId() {
        return unitId;
    }

    public int getRoleId() {
        return roleId;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }
}
