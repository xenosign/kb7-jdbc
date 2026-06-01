package org.example.user.entity;

import lombok.*;

import java.time.LocalDateTime;


public class User {
    private int id;
    private String userId;
    private String name;
    private String password;
    private LocalDateTime createAt;

    public User() {
    }

    public User(int id, String userId, String name, String password, LocalDateTime createAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
