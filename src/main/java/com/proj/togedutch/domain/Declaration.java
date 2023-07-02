package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name="Declaration")
public class Declaration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="declaration_id")
    private int declarationIdx;
    private String content;
    @Column(name="created_at")
    @CreationTimestamp
    private Timestamp createdAt;
    private String status;
    @Column(name="ChatRoom_chatRoom_id")
    private int chatRoomIdx;

    @Builder
    public Declaration(int declarationIdx, String content, Timestamp createdAt, String status, int chatRoomIdx) {
        this.declarationIdx = declarationIdx;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status;
        this.chatRoomIdx = chatRoomIdx;
    }
}
