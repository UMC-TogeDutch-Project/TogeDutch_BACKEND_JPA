package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="ChatLocation")
public class ChatLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chatLocation_id")
    private int chatLocationIdx;

    @Column(name="ChatRoom_chatRoom_id")
    private int chatRoomIdx;

    @Column(name="User_user_id")
    private int userIdx;

    @Column(name="latitude")
    private BigDecimal latitude;

    @Column(name="longitude")
    private BigDecimal longitude;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Builder
    public ChatLocation(int chatRoomIdx, int userIdx, BigDecimal latitude, BigDecimal longitude) {
        this.chatRoomIdx = chatRoomIdx;
        this.userIdx = userIdx;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void updateChatLocation(int chatRoomIdx, BigDecimal latitude, BigDecimal longitude) {
        this.chatRoomIdx = chatRoomIdx;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
