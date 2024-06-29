package com.example.springboot.exception;

public class VersionMismatchException extends RuntimeException {

    public VersionMismatchException(int specifiedVersion, int currentVersion) {
        super("Version mismatch: specified version %d does not match current version %d".formatted(specifiedVersion, currentVersion));
    }
}
