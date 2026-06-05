package org.example.user.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.example.config.MongoConfig;
import org.example.user.entity.User;
import org.example.user.entity.UserPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserMongoPojoRepository {
    // Document 대신 UserPojo(POJO) 타입으로 컬렉션을 직접 다룬다
    private final MongoCollection<UserPojo> collection;

    public UserMongoPojoRepository() {
        MongoDatabase db = MongoConfig.getPojoDatabase();
        this.collection = db.getCollection("user", UserPojo.class);
    }

    public void findAllPojo() {
        List<User> users = new ArrayList<>();
        for (UserPojo pojo : collection.find()) {
            System.out.println(pojo);
        }
    }

//    // id(PK)로 단건 조회
//    public Optional<User> findById(int id) {
//        UserPojo pojo = collection.find(Filters.eq("_id", id)).first();
//        return pojo != null ? Optional.of(pojo.toUser()) : Optional.empty();
//    }
//
//    // user_id(로그인 ID)로 단건 조회
//    public Optional<User> findByUserId(String userId) {
//        UserPojo pojo = collection.find(Filters.eq("user_id", userId)).first();
//        return pojo != null ? Optional.of(pojo.toUser()) : Optional.empty();
//    }
//
//    // 등록 - User -> UserPojo 변환 후 insertOne
//    public int save(User user) {
//        try {
//            collection.insertOne(UserPojo.from(user));
//            System.out.println("사용자 추가 성공");
//            return 1;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//    // 수정
//    public int update(User user) {
//        var result = collection.updateOne(
//                Filters.eq("_id", user.getId()),
//                Updates.combine(
//                        Updates.set("user_id", user.getUserId()),
//                        Updates.set("name", user.getName()),
//                        Updates.set("password", user.getPassword())
//                )
//        );
//        return (int) result.getModifiedCount();
//    }
//
//    // 이름 LIKE 검색
//    public List<User> findByNameLike(String name) {
//        List<User> users = new ArrayList<>();
//        for (UserPojo pojo : collection.find(Filters.regex("name", name))) {
//            users.add(pojo.toUser());
//        }
//        return users;
//    }
//
//    // 삭제
//    public int deleteById(int id) {
//        var result = collection.deleteOne(Filters.eq("_id", id));
//        return (int) result.getDeletedCount();
//    }
}
