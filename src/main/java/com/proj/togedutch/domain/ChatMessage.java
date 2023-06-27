package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name="Chat")
public class ChatMessage {
    public ChatMessage(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private int chatId;

    @Column(name = "ChatRoom_chatRoom_id")
    private int chatRoomId;

    @Column(name = "User_user_id")
    private int userId;

    @Column(name = "created_at")
    private Timestamp createAt;

    @Column(name = "content")
    private String content;

    // DB에는 없는 타입
    @Transient
    private String writer;

    @Transient
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Builder
    public ChatMessage(int chat_id, int chatRoom_chatRoom_id, int user_user_id, Timestamp created_at, String content, String name) {
        this.chatId = chat_id;
        this.chatRoomId = chatRoom_chatRoom_id;
        this.userId = user_user_id;
        this.createAt = created_at;
        this.content = content;
        this.writer = name;
    }

    // 메시지 타입 : 입장, 퇴장, 채팅, 파일송신
    public enum MessageType {
        ENTER, QUIT, TALK
    }
}
