package org.example.entity.user;


import org.example.entity.UserRole;

public class Manager extends BaseUser {
    private Integer expInYear;
    private Long projectId;

    public Manager(Long id, String fullName, String email, String password, Integer expInYear, Long projectId) {
        super(id, fullName, email, password);
        this.expInYear = expInYear;
        this.projectId = projectId;
    }

    @Override public UserRole getRole() { return UserRole.MANAGER; }
    public Integer getExpInYear() { return expInYear; }
    public Long getProjectId() { return projectId; }
}
