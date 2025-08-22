package org.example.entity.user;

import org.example.entity.UserRole;

public class Employee extends BaseUser {
    private Long projectId;
    private String proSkill; // dev, test, java, sql, ...

    public Employee(Long id, String fullName, String email, String password, Long projectId, String proSkill) {
        super(id, fullName, email, password);
        this.projectId = projectId;
        this.proSkill = proSkill;
    }

    @Override public UserRole getRole() { return UserRole.EMPLOYEE; }
    public Long getProjectId() { return projectId; }
    public String getProSkill() { return proSkill; }
}
