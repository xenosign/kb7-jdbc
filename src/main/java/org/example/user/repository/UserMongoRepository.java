package org.example.user.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.example.config.MongoConfig;
import org.example.user.entity.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserMongoRepository {
    private final MongoCollection<Document> collection;

    public UserMongoRepository() {
        MongoDatabase db = MongoConfig.getDatabase();
        this.collection = db.getCollection("user");
    }

    // Document -> User 변환
    private User toUser(Document doc) {
        User user = new User();
        user.setId(doc.getInteger("id", 0));
        user.setUserId(doc.getString("user_id"));
        user.setName(doc.getString("name"));
        user.setPassword(doc.getString("password"));
        Date createdAt = doc.getDate("created_at");
        user.setCreateAt(createdAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        return user;
    }

    // User -> Document 변환
    private Document toDocument(User user) {
        return new Document("id", user.getId())
                .append("user_id", user.getUserId())
                .append("name", user.getName())
                .append("password", user.getPassword())
                .append("created_at", new Date());
    }

    // 전체 조회
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            users.add(toUser(doc));
        }
        return users;
    }

    // id(PK)로 단건 조회
    public Optional<User> findById(int id) {
        Document doc = collection.find(Filters.eq("id", id)).first();
        return doc != null ? Optional.of(toUser(doc)) : Optional.empty();
    }

    // user_id(로그인 ID)로 단건 조회
    public Optional<User> findByUserId(String userId) {
        Document doc = collection.find(Filters.eq("user_id", userId)).first();
        return doc != null ? Optional.of(toUser(doc)) : Optional.empty();
    }

    // 등록
    public int save(User user) {
        try {
            collection.insertOne(toDocument(user));
            System.out.println("사용자 추가 성공");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 수정
    public int update(User user) {
        var result = collection.updateOne(
                Filters.eq("id", user.getId()),
                Updates.combine(
                        Updates.set("user_id", user.getUserId()),
                        Updates.set("name", user.getName()),
                        Updates.set("password", user.getPassword())
                )
        );
        return (int) result.getModifiedCount();
    }

    // 이름 LIKE 검색
    public List<User> findByNameLike(String name) {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find(Filters.regex("name", name))) {
            users.add(toUser(doc));
        }
        return users;
    }

    // 삭제
    public int deleteById(int id) {
        var result = collection.deleteOne(Filters.eq("id", id));
        return (int) result.getDeletedCount();
    }
}
