package com.proj.togedutch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResDto {
    private int userIdx;
    private int keywordIdx;
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

}