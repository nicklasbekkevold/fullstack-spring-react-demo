package com.example.springboot.dto;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

public class UserRoleUpdateDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant validFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant validTo;

    public UserRoleUpdateDto() { }

    public UserRoleUpdateDto(Instant validFrom, Instant validTo) {
        this.validFrom = validFrom == null ? Instant.now() : validFrom;
        this.validTo = validTo;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }
}
