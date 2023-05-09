package com.proj.togedutch.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="Application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="application_id")
    private int applicationIdx;

    @Column(name="status")
    private String status;

    @Column(name="post_id")
    private int postIdx;

    @Column(name="user_id")
    private int userIdx;

    @Column(name="chatRoom_id")
    private int chatRoomIdx;

    @Builder
    public Application(int applicationIdx, String status, int postIdx, int userIdx, int chatRoomIdx) {
        this.applicationIdx = applicationIdx;
        this.status = status;
        this.postIdx = postIdx;
        this.userIdx = userIdx;
        this.chatRoomIdx = chatRoomIdx;
    }
}
