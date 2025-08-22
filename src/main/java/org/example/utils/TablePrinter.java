package org.example.utils;

import org.example.entity.UserRole;
import org.example.entity.user.BaseUser;
import org.example.entity.user.Employee;
import org.example.entity.user.Manager;

import java.util.List;

public class TablePrinter {
    private TablePrinter() {}

    public static void printUsersByProject(long projectId, List<BaseUser> users) {
        String header = String.format("| %-3s | %-8s | %-25s | %-20s | %-10s | %-9s |",
                "ID","Role","Full Name","Email","ProSkill","ExpYear");
        String line = "-".repeat(header.length());
        System.out.println("Project ID: " + projectId);
        System.out.println(line);
        System.out.println(header);
        System.out.println(line);
        for (BaseUser u : users) {
            String role = u.getRole().name();
            String proSkill = "";
            String exp = "";
            if (u.getRole() == UserRole.EMPLOYEE) {
                proSkill = ((Employee) u).getProSkill();
            } else if (u.getRole() == UserRole.MANAGER) {
                Integer e = ((Manager) u).getExpInYear();
                exp = e == null ? "" : e.toString();
            }
            System.out.printf("| %-3d | %-8s | %-25s | %-20s | %-10s | %-9s |%n",
                    u.getId(), role, u.getFullName(), u.getEmail(),
                    proSkill == null ? "" : proSkill, exp);
        }
        System.out.println(line);
        if (users.isEmpty()) System.out.println("(Không có nhân sự trong project này)");
    }
}
