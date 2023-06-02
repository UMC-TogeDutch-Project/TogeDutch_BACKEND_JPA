package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="ChatPhoto")
public class ChatPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chatPhoto_id")
    private int chatPhotoIdx;

    @Column(name="ChatRoom_chatRoom_id")
    private int chatRoomIdx;

    @Column(name="User_user_id")
    private int userIdx;

    @Column(name="image")
    private String image;

    @Column(name="created_at")
    private Timestamp createdAt;

    // Dto to Entity
    @Builder
    public ChatPhoto(int chatRoomIdx, int userIdx, String image) {
        this.chatRoomIdx = chatRoomIdx;
        this.userIdx = userIdx;
        this.image = image;
    }
}
