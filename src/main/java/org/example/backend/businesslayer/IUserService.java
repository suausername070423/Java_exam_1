package org.example.backend.businesslayer;

import org.example.entity.user.BaseUser;

import java.util.List;

public interface IUserService {
    int seedInitialData() throws Exception;
    List<BaseUser> findUsersByProject(long projectId) throws Exception;
    boolean loginAdmin(String email, String password) throws Exception;
    long createEmployee(String fullName, String email) throws Exception;
}
