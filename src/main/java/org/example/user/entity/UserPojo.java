package org.example.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPojo {
    @BsonProperty("id")
    private int id;
    @BsonProperty("user_id")
    private String userId;
    private String name;
    private String password;
    @BsonProperty("created_at")
    private Date createdAt;

    // User 엔티티로 변환
    public User toUser() {
        User user = new User();
        user.setId(this.id);
        user.setUserId(this.userId);
        user.setName(this.name);
        user.setPassword(this.password);
        if (this.createdAt != null) {
            user.setCreateAt(this.createdAt.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDateTime());
        }
        return user;
    }

    // User 엔티티로부터 생성
    public static UserPojo from(User user) {
        return new UserPojo(
                user.getId(),
                user.getUserId(),
                user.getName(),
                user.getPassword(),
                new Date()
        );
    }
}
