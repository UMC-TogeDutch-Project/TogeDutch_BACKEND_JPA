package com.proj.togedutch.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(name="Post_post_id")
    private int postIdx;

    // 공고 업로드 한 유저의 id
    @Column(name="Post_User_user_id")
    private int uploaderIdx;

    @Column(name="ChatRoom_chatRoom_id")
    private Integer chatRoomIdx;

    // 공고를 신청한 유저의 id
    @Column(name="User_user_id")
    private int userIdx;

    @Builder
    public Application(String status, int postIdx, int uploaderIdx, int chatRoomIdx, int userIdx) {
        this.status = status;
        this.postIdx = postIdx;
        this.uploaderIdx = uploaderIdx;
        this.chatRoomIdx = chatRoomIdx;
        this.userIdx = userIdx;
    }


    public void modifyApplication(String status){
        this.status = status;
    }

    public void modifyChatRoomStatus(){
        this.chatRoomIdx = null;
    }

}
