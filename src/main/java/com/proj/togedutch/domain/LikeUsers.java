package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="LikeUsers")
public class LikeUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="like_id")
    private int likeIdx;

    @Column(name="Post_post_id")
    private int postIdx;

    // 공고 업로드 한 유저의 id
    @Column(name="Post_User_user_id")
    private int uploaderIdx;

    // 공고를 관심 목록에 담은 유저의 id
    @Column(name="User_user_id")
    private int keeperIdx;

    @Builder
    public LikeUsers (int postIdx, int uploaderIdx, int keeperIdx){
        this.postIdx = postIdx;
        this.uploaderIdx = uploaderIdx;
        this.keeperIdx = keeperIdx;
    }
}
