package org.example.user;

import org.example.user.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserMain {

    private static final String URL = "jdbc:mysql://localhost:3306/kb7-jdbc";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== 회원 관리 프로그램 ======");
            System.out.println("1. 회원 목록 조회");
            System.out.println("2. 회원 추가");
            System.out.println("3. 특정 이름이 포함된 유저 검색");
            System.out.println("4. 회원 삭제");
            System.out.println("5. 회원 정보 수정");
            System.out.println("6. 종료");
            System.out.print("원하는 작업 번호를 입력하세요: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                // 1. 회원 목록 조회
                String sql = "SELECT * FROM `user`";
                List<User> users = new ArrayList<>();
                try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUserId(rs.getString("user_id"));
                        user.setName(rs.getString("name"));
                        user.setPassword(rs.getString("password"));
                        user.setCreateAt(rs.getTimestamp("create_at") != null
                                ? rs.getTimestamp("create_at").toLocalDateTime() : null);
                        users.add(user);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (User user : users) {
                    System.out.println(user.toString());
                }

            } else if (choice == 2) {
                // 2. 회원 추가
                System.out.print("USER ID: ");
                String userId = scanner.nextLine();
                System.out.print("이름: ");
                String name = scanner.nextLine();
                System.out.print("비밀번호: ");
                String password = scanner.nextLine();

                User newUser = new User();
                newUser.setUserId(userId);
                newUser.setName(name);
                newUser.setPassword(password);

                String sql = "INSERT INTO `user` (user_id, name, password) VALUES (?, ?, ?)";
                try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                    pstmt.setString(1, newUser.getUserId());
                    pstmt.setString(2, newUser.getName());
                    pstmt.setString(3, newUser.getPassword());
                    pstmt.executeUpdate();

                    try (ResultSet keys = pstmt.getGeneratedKeys()) {
                        if (keys.next()) {
                            System.out.println("추가된 회원 PK: " + keys.getInt(1));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (choice == 3) {
                // 3. 이름 LIKE 검색
                System.out.print("검색할 이름: ");
                String name = scanner.nextLine();

                String sql = "SELECT * FROM `user` WHERE name LIKE ?";
                try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, "%" + name + "%");
                    List<User> result = new ArrayList<>();
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            User user = new User();
                            user.setId(rs.getInt("id"));
                            user.setUserId(rs.getString("user_id"));
                            user.setName(rs.getString("name"));
                            user.setPassword(rs.getString("password"));
                            user.setCreateAt(rs.getTimestamp("create_at") != null
                                    ? rs.getTimestamp("create_at").toLocalDateTime() : null);
                            result.add(user);
                        }
                    }
                    for (User user : result) {
                        System.out.println(user.toString());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (choice == 4) {
                // 4. 회원 삭제
                System.out.print("삭제할 회원의 PK: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                String sql = "DELETE FROM `user` WHERE id = ?";
                try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setInt(1, id);
                    int deleted = pstmt.executeUpdate();
                    System.out.println("삭제된 행 수: " + deleted);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (choice == 5) {
                // 5. 회원 정보 수정
                System.out.print("수정할 회원의 PK: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("새 이름: ");
                String newName = scanner.nextLine();
                System.out.print("새 비밀번호: ");
                String newPassword = scanner.nextLine();

                User updateUser = new User();
                updateUser.setId(id);
                updateUser.setName(newName);
                updateUser.setPassword(newPassword);

                String sql = "UPDATE `user` SET name = ?, password = ? WHERE id = ?";
                try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, updateUser.getName());
                    pstmt.setString(2, updateUser.getPassword());
                    pstmt.setInt(3, updateUser.getId());
                    int updated = pstmt.executeUpdate();
                    System.out.println("수정된 행 수: " + updated);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (choice == 6) {
                // 6. 종료
                System.out.println("프로그램을 종료합니다.");
                break;

            } else {
                System.out.println("잘못된 번호입니다. 다시 입력하세요.");
            }
        }

        scanner.close();
    }
}
