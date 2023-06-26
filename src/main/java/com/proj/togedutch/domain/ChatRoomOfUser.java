package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name="ChatRoomOfUser")
public class ChatRoomOfUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="chatRoom_id")
    private int chatRoomIdx;


    @Column(name="user_id")
    private int userIdx;

    @Column(name="status")
    private byte status;

    @Column(name="is_read")
    private int is_read;

    @Column(name = "updated_at")
    private Timestamp updated_at;


    @Builder
    public ChatRoomOfUser(int chatRoomIdx, int userIdx,byte status, int is_read, Timestamp updated_at) {
        this.chatRoomIdx = chatRoomIdx;
        this.userIdx = userIdx;
        this.status = status;
        this.is_read = is_read;
        this.updated_at = updated_at;
    }
}
