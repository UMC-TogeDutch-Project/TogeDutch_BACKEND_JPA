package com.proj.togedutch.dto;

import com.proj.togedutch.repository.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class MatchingResDto {
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
    private String matchingResult;

    public MatchingResDto(UserRepository.UserInfo user){
        this.userIdx = user.getUser_id();
        this.keywordIdx = user.getKeyword_keyword_id();
        this.name = user.getName();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.image = user.getImage();
        this.status = user.getStatus();
        this.created_at = user.getCreated_at();
        this.updated_at = user.getUpdated_at();
        this.latitude = user.getLatitude();
        this.longitude = user.getLongitude();
        this.jwt = null;
    }
}
