package org.example.user.repository;

import org.example.config.JDBCUtil;
import org.example.user.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    // 전체 조회
    public List<User> findAll() {
        String sql = "SELECT * FROM `user`";
        List<User> users = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserId(rs.getString("user_id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // id(PK)로 단건 조회
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM `user` WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserId(rs.getString("user_id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setCreateAt(rs.getTimestamp("create_at") != null
                            ? rs.getTimestamp("create_at").toLocalDateTime()
                            : null);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // user_id(로그인 ID)로 단건 조회
    public Optional<User> findByUserId(String userId) {
        String sql = "SELECT * FROM `user` WHERE user_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserId(rs.getString("user_id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setCreateAt(rs.getTimestamp("create_at") != null
                            ? rs.getTimestamp("create_at").toLocalDateTime()
                            : null);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

//    // 등록
//    public int save(User user) {
//        String sql = "INSERT INTO `user` (user_id, name, password) VALUES ('" +
//                user.getUserId() + "', '" +
//                user.getName() + "', '" +
//                user.getPassword() + "')";
//
//        try (Connection conn = JDBCUtil.getConnection();
//             Statement stmt = conn.createStatement()) {
//            int affectedRow = stmt.executeUpdate(sql);
//
//            if (affectedRow > 0) {
//                System.out.println("사용자 추가 성공");
//            } else {
//                System.out.println("사용자 추가 실패");
//            }
//
//            return affectedRow;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return -1;
//    }

    // 등록
    public int save(User user) {
        String sql = "INSERT INTO `user` (user_id, name, password) VALUES (?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            int affectedRow = pstmt.executeUpdate();

            if (affectedRow > 0) {
                System.out.println("사용자 추가 성공");
            } else {
                System.out.println("사용자 추가 실패");
            }
            return affectedRow;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 수정
    public int update(User user) {
        String sql = "UPDATE `user` SET name = ?, password = ? WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 이름 LIKE 검색
    public List<User> findByNameLike(String name) {
        String sql = "SELECT * FROM `user` WHERE name LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserId(rs.getString("user_id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    user.setCreateAt(rs.getTimestamp("create_at") != null
                            ? rs.getTimestamp("create_at").toLocalDateTime()
                            : null);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 삭제
    public int deleteById(int id) {
        String sql = "DELETE FROM `user` WHERE id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
