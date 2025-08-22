package org.example.frontend;

import org.example.backend.presentationlayer.UserController;
import org.example.entity.user.BaseUser;
import org.example.utils.JdbcUtils;
import org.example.utils.TablePrinter;

import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        UserController controller = new UserController();
        Scanner sc = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("\n=== MENU ===");
                System.out.println("1. Seed dữ liệu mẫu (Admin/Manager/Employee)");
                System.out.println("2. Tra cứu theo projectId (in bảng)");
                System.out.println("3. Login Admin");
                System.out.println("4. Tạo Employee (password=123456)");
                System.out.println("0. Thoát");
                System.out.print("Chọn: ");
                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1" -> {
                        int rows = controller.seed();
                        System.out.println(rows == 0
                                ? "Bảng đã có dữ liệu, bỏ qua seed."
                                : "Seed thành công, inserted rows = " + rows);
                    }
                    case "2" -> {
                        System.out.print("Nhập projectId: ");
                        long pid = Long.parseLong(sc.nextLine().trim());
                        List<BaseUser> users = controller.findUsersByProject(pid);
                        TablePrinter.printUsersByProject(pid, users);
                    }
                    case "3" -> {
                        System.out.print("Email admin: ");
                        String email = sc.nextLine().trim();
                        System.out.print("Password (6-12 ký tự): ");
                        String pass = sc.nextLine();
                        boolean ok = controller.loginAdmin(email, pass);
                        System.out.println(ok ? "Đăng nhập thành công!" : "Sai email/password hoặc không phải admin.");
                    }
                    case "4" -> {
                        System.out.print("FullName: ");
                        String fullName = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        try {
                            long id = controller.createEmployee(fullName, email);
                            System.out.println("Tạo employee thành công, id = " + id + ", password mặc định = 123456");
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Lỗi validate: " + ex.getMessage());
                        }
                    }
                    case "0" -> {
                        System.out.println("Tạm biệt!");
                        JdbcUtils.disconnect();
                        return;
                    }
                    default -> System.out.println("Lựa chọn không hợp lệ.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try { JdbcUtils.disconnect(); } catch (Exception ignore) {}
        }
    }
}
