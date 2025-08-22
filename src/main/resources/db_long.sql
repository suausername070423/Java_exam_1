DROP DATABASE IF EXISTS ProductManagement;
CREATE DATABASE IF NOT EXISTS ProductManagement CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE ProductManagement;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  full_name    VARCHAR(100) NOT NULL,
  email        VARCHAR(120) NOT NULL UNIQUE,
  password     VARCHAR(255) NOT NULL,
  exp_in_year  TINYINT UNSIGNED NULL,
  pro_skill    VARCHAR(50) NULL,
  project_id   BIGINT NULL,
  role         ENUM('manager','employee','admin') NOT NULL,

  CHECK ( (role <> 'manager')  OR (pro_skill IS NULL) ),
  CHECK ( (role <> 'employee') OR (exp_in_year IS NULL) ),
  CHECK ( (role <> 'admin')    OR (exp_in_year IS NULL AND pro_skill IS NULL) )
);

/*
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_project ON users(project_id);

-- INSERT
-- Manager: pro_skill = NULL, role='manager'
INSERT INTO users(full_name, email, password, exp_in_year, project_id, role)
VALUES ('Nguyen Van A', 'a.manager@company.com', '123456', 5, 101, 'manager');

-- Employee: exp_in_year = NULL, role='employee'
INSERT INTO users(full_name, email, password, project_id, pro_skill, role)
VALUES ('Tran Thi B', 'b.employee@company.com', '123456', 102, 'java', 'employee');

-- Admin: exp_in_year = NULL, pro_skill = NULL, role='admin'
INSERT INTO users(full_name, email, password, role)
VALUES ('Le Van C', 'c.admin@company.com', '123456', 'admin');
*/
