package org.example.user.repository;

import org.apache.ibatis.session.SqlSession;
import org.example.config.MyBatisConfig;
import org.example.user.entity.User;
import org.example.user.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

public class UserMybatisRepository {
    private static final String NAMESPACE = "org.example.user.mapper.UserMapper.";

    // 전체 조회
    public List<User> findAll() {
        try (SqlSession session = MyBatisConfig.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.findAll();
        }
    }

    // 전체 조회
    public List<User> findAll2() {
        try (SqlSession session = MyBatisConfig.getSqlSession()) {
          return session.selectList(NAMESPACE + "findAll");
        }
    }

//    // id(PK)로 단건 조회
//    public Optional<User> findById(int id) {
//        try (SqlSession session = MyBatisConfig.getSqlSession()) {
//            UserMapper mapper = session.getMapper(UserMapper.class);
//            return Optional.ofNullable(mapper.findById(id));
//        }
//    }
//
//    // user_id(로그인 ID)로 단건 조회
//    public Optional<User> findByUserId(String userId) {
//        try (SqlSession session = MyBatisConfig.getSqlSession()) {
//            UserMapper mapper = session.getMapper(UserMapper.class);
//            return Optional.ofNullable(mapper.findByUserId(userId));
//        }
//    }

    // 이름 LIKE 검색
    public List<User> findByNameLike(String name) {
        try (SqlSession session = MyBatisConfig.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.findByNameLike("%" + name + "%");
        }
    }

    // 등록
    public int save(User user) {
        try (SqlSession session = MyBatisConfig.getSqlSession()) {
            System.out.println("추가 할 회원 Entity 는? : " + user);
            UserMapper mapper = session.getMapper(UserMapper.class);
            int result = mapper.save(user);
            session.commit();
            System.out.println("추가 된 회원 Entity 는? : " + user);
            return result;
        }
    }

    // 수정
    public int update(User user) {
        try (SqlSession session = MyBatisConfig.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            int result = mapper.update(user);
            session.commit();
            return result;
        }
    }

    // 삭제
    public int deleteById(int id) {
        try (SqlSession session = MyBatisConfig.getSqlSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            int result = mapper.deleteById(id);
            session.commit();
            return result;
        }
    }
}
