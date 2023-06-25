package com.proj.togedutch.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userIdx;

    @Column(name="Keyword_keyword_id")
    private  Integer keywordIdx;

    private String name;

    private String role;

    private String email;

    private String password;

    private String phone;

    private String image;

    private String status; //일반 사용자 or 음식점 사장님

    private Timestamp created_at;

    private Timestamp updated_at;

    private double latitude;

    private double longitude;

    private String jwt;

    public User(int userIdx, int keywordIdx, String name, String role, String email, String password, String phone, String image, String status, Timestamp created_at, Timestamp updated_at, double latitude, double longitude) {
        this.userIdx = userIdx;
        this.keywordIdx = keywordIdx;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.image = image;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public User(int userIdx, int keywordIdx, String name, String role, String email, String password, String phone, String image, String status, double latitude, double longitude) {
        this.userIdx = userIdx;
        this.keywordIdx = keywordIdx;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.image = image;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public User(String Status) {
        this.status = Status;
    }

    public User(int userIdx, String name){
        this.userIdx = userIdx;
        this.name = name;
    }

    public User(int keywordIdx, String name, String role, String email, String password, String phone, String image, String status, double latitude, double longitude) {
        this.keywordIdx = keywordIdx;
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.image = image;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}