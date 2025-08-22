package org.example.entity;

public enum UserRole {
    MANAGER, EMPLOYEE, ADMIN;

    public static UserRole fromDb(String s) {
        return UserRole.valueOf(s.toUpperCase());
    }
    public String toDb() {
        return name().toLowerCase();
    }
}
