package org.example.user.service;

import org.example.config.JDBCUtil;
import org.example.user.dto.UserCreateRequest;
import org.example.user.dto.UserResponse;
import org.example.user.entity.User;
import org.example.user.repository.UserMybatisRepository;
import org.example.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository = new UserRepository(JDBCUtil.getConnection());
    private final UserMybatisRepository userMybatisRepository = new UserMybatisRepository();

    // 1. 회원 목록 조회
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();

        for (User user : users) {
            UserResponse dto = new UserResponse();
            dto.setId(user.getId());
            dto.setUserId(user.getUserId());
            dto.setName(user.getName());
            dto.setCreateAt(user.getCreateAt());
            result.add(dto);
        }

        return result;
    }

    // 1. 회원 목록 조회
    public List<UserResponse> getAllUsersLambda() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUserId(), user.getName(), user.getCreateAt()))
                .collect(Collectors.toList());
    }

    // 2. 회원 추가
    public int addUser(UserCreateRequest request) {
        // DTO 를 Entity 로 변환하여 전달!
        User user = new User();
        user.setUserId(request.getUserId());
        user.setName(request.getName());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    // 3. 특정 이름이 포함된 유저 검색
    public List<User> searchByName(String name) {
        List<User> allUsers = userRepository.findAll();
        List<User> result = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getName().contains(name)) {
                result.add(user);
            }
        }
        return result;
    }

    public List<User> searchByNameByDB(String name) {
        return userRepository.findAll().stream()
                .filter(user -> user.getName().contains(name))
                .collect(Collectors.toList());
    }

    // 3-2. 이름 LIKE 검색 (DB)
    public List<User> searchByNameLike(String name) {
        return userRepository.findByNameLike(name);
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
