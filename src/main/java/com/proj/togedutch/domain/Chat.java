package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name="Chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private int id;

    @Column(name="ChatRoom_chatRoom_id")
    private int chatRoomIdx;

    @Column(name="User_user_id")
    private int userIdx;

    @Column(name="created_at")
    private Timestamp created_at;

    @Column(name="content")
    private String content;

    @Builder
    public Chat(int chatRoomIdx, int userIdx, Timestamp created_at, String content) {
        this.chatRoomIdx = chatRoomIdx;
        this.userIdx = userIdx;
        this.created_at = created_at;
        this.content = content;
    }
}

