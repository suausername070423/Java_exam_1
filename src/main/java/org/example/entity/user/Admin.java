package org.example.entity.user;

import org.example.entity.UserRole;

public class Admin extends BaseUser {
    public Admin(Long id, String fullName, String email, String password) {
        super(id, fullName, email, password);
    }
    @Override public UserRole getRole() { return UserRole.ADMIN; }
}
