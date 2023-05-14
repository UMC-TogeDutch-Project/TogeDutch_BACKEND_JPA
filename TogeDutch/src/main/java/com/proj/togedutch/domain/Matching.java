package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="Matching")
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Matching_Id")
    private int matchingIdx;

    @Column(name="user_first_id")
    private Integer UserFirstIdx;

    @Column(name="user_second_id")
    private Integer UserSecondIdx;

    @Column(name="user_third_id")
    private Integer UserThirdIdx;

    @Column(name="count")
    private Integer count;

    @Column(name="Post_post_id")
    private Integer postIdx;

    public void updateCountToOne(int userFirstIdx, int count) {
        this.UserFirstIdx = userFirstIdx;
        this.count = count;
    }

    public void updateCountToTwo(int userSecondIdx, int count) {
        this.UserSecondIdx = userSecondIdx;
        this.count = count;
    }

    public void updateCountToThree(int userThirdIdx, int count) {
        this.UserThirdIdx = userThirdIdx;
        this.count = count;
    }

    @Builder
    public Matching(int userFirstIdx, int count,int postIdx) {
        this.UserFirstIdx = userFirstIdx;
        this.count = count;
        this.postIdx = postIdx;
    }
}
