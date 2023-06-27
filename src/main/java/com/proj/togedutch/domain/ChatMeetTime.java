package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="ChatMeetTime")
public class ChatMeetTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chatMeetTime_id")
    private int chatMeetTimeIdx;

    @Column(name="ChatRoom_chatRoom_id")
    private int chatRoomIdx;

    @Column(name="User_user_id")
    private int userIdx;

    @Column(name="meetTime")
    private DateTime meetTime;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Builder
    public ChatMeetTime(int chatRoomIdx, int userIdx, DateTime meetTime,Timestamp createdAt) {
        this.chatRoomIdx = chatRoomIdx;
        this.userIdx = userIdx;
        this.meetTime = meetTime;
        this.createdAt = createdAt;
    }
}
