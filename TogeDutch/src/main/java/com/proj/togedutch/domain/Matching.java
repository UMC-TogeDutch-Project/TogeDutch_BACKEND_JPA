package com.proj.togedutch.domain;

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
    private int UserFirstIdx;

    @Column(name="user_second_id")
    int UserSecondIdx;

    @Column(name="user_third_id")
    int UserThirdIdx;

    @Column(name="count")
    int count;

    @Column(name="Post_post_id")
    int postIdx;


}
