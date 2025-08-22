package org.example.backend.presentationlayer;

import org.example.backend.businesslayer.IUserService;
import org.example.backend.businesslayer.UserService;
import org.example.entity.user.BaseUser;

import java.util.List;

public class UserController {
    private final IUserService service = new UserService();

    public int seed() throws Exception { return service.seedInitialData(); }

    public List<BaseUser> findUsersByProject(long projectId) throws Exception {
        return service.findUsersByProject(projectId);
    }

    public boolean loginAdmin(String email, String password) throws Exception {
        return service.loginAdmin(email, password);
    }

    public long createEmployee(String fullName, String email) throws Exception {
        return service.createEmployee(fullName, email);
    }
}
