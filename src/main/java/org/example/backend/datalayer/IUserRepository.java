package org.example.backend.datalayer;

import org.example.entity.user.BaseUser;

import java.util.List;

public interface IUserRepository {
    int seedInitialData() throws Exception;

    List<BaseUser> findUsersByProject(long projectId) throws Exception;

    /** @return true nếu login admin thành công */
    boolean loginAdmin(String email, String password) throws Exception;

    /** Tạo mới Employee với password mặc định */
    long createEmployee(String fullName, String email) throws Exception;
}
