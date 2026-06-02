package org.example.user.mapper;

import org.example.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserMapper {
    List<User> findAll();
    User findById(int id);
    User findByUserId(String userId);
    List<User> findByNameLike(String name);
    int save(User user);
    int update(User user);
    int deleteById(int id);
}
