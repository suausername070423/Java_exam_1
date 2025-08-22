package org.example.backend.datalayer;

import org.example.entity.UserRole;
import org.example.entity.user.BaseUser;
import org.example.entity.user.Employee;
import org.example.entity.user.Manager;
import org.example.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {

    @Override
    public int seedInitialData() throws Exception {
        // Chỉ seed khi bảng đang trống
        String countSql = "SELECT COUNT(*) FROM users";
        try (PreparedStatement ps = JdbcUtils.connect().prepareStatement(countSql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            if (rs.getLong(1) > 0) return 0;
        }

        String insert = "INSERT INTO users(full_name, email, password, exp_in_year, pro_skill, project_id, role) " +
                "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = JdbcUtils.connect().prepareStatement(insert)) {
            int rows = 0;
            // Manager
            rows += insertUser(ps, "Pham Manager", "pm@company.com", "123456", 7, null, 1001L, UserRole.MANAGER);
            rows += insertUser(ps, "Tran Lead", "lead@company.com", "123456", 5, null, 1002L, UserRole.MANAGER);
            // Employee
            rows += insertUser(ps, "Nguyen Dev", "dev@company.com", "123456", null, "java", 1001L, UserRole.EMPLOYEE);
            rows += insertUser(ps, "Le Tester", "test@company.com", "123456", null, "test", 1002L, UserRole.EMPLOYEE);
            rows += insertUser(ps, "Bui SQL", "sql@company.com", "123456", null, "sql", 1001L, UserRole.EMPLOYEE);
            // Admin
            rows += insertUser(ps, "System Admin", "admin@company.com", "admin123", null, null, null, UserRole.ADMIN);
            return rows;
        }
    }

    private int insertUser(PreparedStatement ps, String fullName, String email, String password,
                           Integer expInYear, String proSkill, Long projectId, UserRole role) throws SQLException {
        ps.setString(1, fullName);
        ps.setString(2, email);
        ps.setString(3, password);
        if (expInYear == null) ps.setNull(4, Types.TINYINT); else ps.setInt(4, expInYear);
        if (proSkill == null)  ps.setNull(5, Types.VARCHAR); else ps.setString(5, proSkill);
        if (projectId == null) ps.setNull(6, Types.BIGINT); else ps.setLong(6, projectId);
        ps.setString(7, role.toDb());
        return ps.executeUpdate();
    }

    @Override
    public List<BaseUser> findUsersByProject(long projectId) throws Exception {
        String sql = """
            SELECT id, full_name, email, password, exp_in_year, pro_skill, project_id, role
            FROM users
            WHERE project_id = ? AND role IN ('manager','employee')
            ORDER BY role, full_name
            """;
        try (PreparedStatement ps = JdbcUtils.connect().prepareStatement(sql)) {
            ps.setLong(1, projectId);
            try (ResultSet rs = ps.executeQuery()) {
                List<BaseUser> list = new ArrayList<>();
                while (rs.next()) {
                    String role = rs.getString("role");
                    Long id = rs.getLong("id");
                    String fullName = rs.getString("full_name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    if ("manager".equals(role)) {
                        Integer exp = (Integer) rs.getObject("exp_in_year");
                        Long proj = (Long) rs.getObject("project_id");
                        list.add(new Manager(id, fullName, email, password, exp, proj));
                    } else {
                        Long proj = (Long) rs.getObject("project_id");
                        String proSkill = rs.getString("pro_skill");
                        list.add(new Employee(id, fullName, email, password, proj, proSkill));
                    }
                }
                return list;
            }
        }
    }

    @Override
    public boolean loginAdmin(String email, String password) throws Exception {
        String sql = "SELECT id FROM users WHERE role='admin' AND email=? AND password=? LIMIT 1";
        try (PreparedStatement ps = JdbcUtils.connect().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public long createEmployee(String fullName, String email) throws Exception {
        String sql = "INSERT INTO users(full_name, email, password, exp_in_year, pro_skill, project_id, role) " +
                "VALUES (?, ?, '123456', NULL, NULL, NULL, 'employee')";
        try (PreparedStatement ps = JdbcUtils.connect().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("Create employee failed");
    }
}
