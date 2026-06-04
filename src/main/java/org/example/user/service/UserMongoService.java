package org.example.user.service;

import org.example.user.dto.UserCreateRequest;
import org.example.user.dto.UserResponse;
import org.example.user.entity.User;
import org.example.user.repository.UserMongoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserMongoService {
    private final UserMongoRepository userMongoRepository = new UserMongoRepository();

    // 1. 회원 목록 조회
    public List<UserResponse> getAllUsers() {
        return userMongoRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUserId(), user.getName(), user.getCreateAt()))
                .collect(Collectors.toList());
    }

    // 2. 회원 추가
    public int addUser(UserCreateRequest request) {
        User user = new User();
        user.setUserId(request.getUserId());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        return userMongoRepository.save(user);
    }

    // 3. 특정 이름이 포함된 유저 검색
    public List<User> searchByName(String name) {
        return userMongoRepository.findByNameLike(name);
    }

    // 4. 회원 삭제
    public int deleteUser(int id) {
        return userMongoRepository.deleteById(id);
    }

    // 5. 회원 정보 수정
    public int updateUser(User user) {
        return userMongoRepository.update(user);
    }
}
