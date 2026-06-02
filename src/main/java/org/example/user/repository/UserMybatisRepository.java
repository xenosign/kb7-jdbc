package org.example.user.repository;

import org.apache.ibatis.session.SqlSession;
import org.example.config.MyBatisUtil;
import org.example.user.entity.User;
import org.example.user.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

public class UserMybatisRepository {

    // 전체 조회
    public List<User> findAll() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.findAll();
        }
    }

    // id(PK)로 단건 조회
    public Optional<User> findById(int id) {
        try (SqlSession session = MyBatisUtil.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return Optional.ofNullable(mapper.findById(id));
        }
    }

    // user_id(로그인 ID)로 단건 조회
    public Optional<User> findByUserId(String userId) {
        try (SqlSession session = MyBatisUtil.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return Optional.ofNullable(mapper.findByUserId(userId));
        }
    }

    // 이름 LIKE 검색
    public List<User> findByNameLike(String name) {
        try (SqlSession session = MyBatisUtil.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.findByNameLike("%" + name + "%");
        }
    }

    // 등록
    public int save(User user) {
        try (SqlSession session = MyBatisUtil.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.save(user);
        }
    }

    // 수정
    public int update(User user) {
        try (SqlSession session = MyBatisUtil.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.update(user);
        }
    }

    // 삭제
    public int deleteById(int id) {
        try (SqlSession session = MyBatisUtil.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.deleteById(id);
        }
    }
}
