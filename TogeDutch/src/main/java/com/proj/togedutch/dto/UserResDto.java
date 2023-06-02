package com.proj.togedutch.dto;

import com.proj.togedutch.domain.User;
import com.proj.togedutch.repository.MatchingRepository;
import com.proj.togedutch.repository.UserRepository;
import lombok.*;

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

    // Entity to DTO
    @Builder
    public UserResDto(UserRepository.UserInfo user) {
        this.userIdx = user.getUser_id();
        this.keywordIdx = user.getKeyword_keyword_id();
        this.name = user.getName();
        this.role = user.getRole();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.image = user.getImage() == null ? null : imageConvert(user.getImage());
        this.image = user.getImage();
        this.status = user.getStatus();
        this.created_at = user.getCreated_at();
        this.updated_at = user.getUpdated_at();
        this.latitude = user.getLatitude();
        this.longitude = user.getLongitude();
        this.jwt = null;
    }

    // ASCII TO String
    public String imageConvert(String image) {
        String[] byteStrings = image.split(",");
        byte[] imageBytes = new byte[byteStrings.length];

        for (int i = 0; i < byteStrings.length; i++) {
            imageBytes[i] = Byte.parseByte(byteStrings[i].trim());
        }

        return new String(imageBytes);
    }
}
