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
        findAllUsers();
        addUser();
        findAllUsers();

        // addUser 에 코드 진화 사항 추가 > Con 관리 / Try with resources 적용
    }

    public static void findAllUsers() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        System.out.println("1. 사용자 전체 조회");

        String sql = "SELECT * FROM `user`";
        List<User> users = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserId(rs.getString("user_id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
                users.add(user);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    public static void addUser() {
        System.out.println("2. 사용자 추가");

        Scanner scanner = new Scanner(System.in);

        System.out.print("아이디 입력: ");
        String userId = scanner.nextLine();

        System.out.print("이름 입력: ");
        String name = scanner.nextLine();

        System.out.print("비밀번호 입력: ");
        String password = scanner.nextLine();

        String sql = "INSERT INTO `user` (user_id, name, password)" +
                " VALUES ('" + userId + "', '" + name + "', '" + password + "')";

        try (
                Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement()
        ) {
            int result = stmt.executeUpdate(sql);

            if (result > 0) {
                System.out.println("사용자 추가 성공");
            } else {
                System.out.println("사용자 추가 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
