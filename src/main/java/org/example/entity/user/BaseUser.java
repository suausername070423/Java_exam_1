package org.example.entity.user;

import org.example.entity.UserRole;

public abstract class BaseUser {
    protected Long id;
    protected String fullName;
    protected String email;
    protected String password;

    protected BaseUser(Long id, String fullName, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public abstract UserRole getRole();

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return "User{id=" + id + ", fullName='" + fullName + "', email='" + email + "', role=" + getRole() + "}";
    }
}
