package com.proj.togedutch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;v
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name="ChatRoom")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chatRoom_id")
    private int chatRoomIdx;
    @Column(name="created_at")
    private Timestamp createdAt;

    @Builder
    public ChatRoom(int chatRoomIdx, Timestamp createdAt) {
        this.chatRoomIdx = chatRoomIdx;
        this.createdAt = createdAt;
    }
}