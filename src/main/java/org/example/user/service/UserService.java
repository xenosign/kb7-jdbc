package org.example.user.service;

import org.example.user.entity.User;
import org.example.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    // 1. 회원 목록 조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 2. 회원 추가
    public int addUser(User user) {
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
