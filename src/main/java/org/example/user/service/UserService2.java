package org.example.user.service;

import org.example.config.JDBCUtil;
import org.example.user.dto.UserResponse;
import org.example.user.entity.User;
import org.example.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService2 {

    private final UserRepository userRepository = new UserRepository(JDBCUtil.getConnection());

    // User -> UserDto 변환
    private UserResponse toDto(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setCreateAt(user.getCreateAt());
        return dto;
    }

    // 1. 회원 목록 조회
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();
        for (User user : users) {
            result.add(toDto(user));
        }
        return result;
    }

    // 2. 회원 추가
    public int addUser(User user) {
        return userRepository.save(user);
    }

    // 3. 특정 이름이 포함된 유저 검색
    public List<UserResponse> searchByName(String name) {
        List<User> allUsers = userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getName().contains(name)) {
                result.add(toDto(user));
            }
        }
        return result;
    }

    // 3-2. 이름 LIKE 검색 (DB)
    public List<UserResponse> searchByNameLike(String name) {
        List<User> users = userRepository.findByNameLike(name);
        List<UserResponse> result = new ArrayList<>();
        for (User user : users) {
            result.add(toDto(user));
        }
        return result;
    }

    // 4. 회원 삭제
    public int deleteUser(int id) {
        return userRepository.deleteById(id);
    }

    // 5. 회원 정보 수정
    public int updateUser(User user) {
        return userRepository.update(user);
    }
}
