package org.example.backend.businesslayer;

import org.example.backend.datalayer.IUserRepository;
import org.example.backend.datalayer.UserRepository;
import org.example.entity.user.BaseUser;
import org.example.utils.ValidationUtils;

import java.util.List;

public class UserService implements IUserService {
    private final IUserRepository repo = new UserRepository();

    @Override
    public int seedInitialData() throws Exception {
        return repo.seedInitialData();
    }

    @Override
    public List<BaseUser> findUsersByProject(long projectId) throws Exception {
        if (projectId <= 0) throw new IllegalArgumentException("projectId phải > 0");
        return repo.findUsersByProject(projectId);
    }

    @Override
    public boolean loginAdmin(String email, String password) throws Exception {
        if (!ValidationUtils.isValidEmail(email)) return false;
        if (!ValidationUtils.isValidPassword(password)) return false;
        return repo.loginAdmin(email, password);
    }

    @Override
    public long createEmployee(String fullName, String email) throws Exception {
        if (!ValidationUtils.isValidFullName(fullName))
            throw new IllegalArgumentException("Fullname chỉ chứa chữ và khoảng trắng");
        if (!ValidationUtils.isValidEmail(email))
            throw new IllegalArgumentException("Email không hợp lệ");
        return repo.createEmployee(fullName.trim(), email.trim().toLowerCase());
    }
}
