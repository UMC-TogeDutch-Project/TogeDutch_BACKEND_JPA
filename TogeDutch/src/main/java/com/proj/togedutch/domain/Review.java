package com.proj.togedutch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.PseudoColumnUsage;
import java.util.Date;

import javax.persistence.*;
import com.proj.togedutch.domain.Post;

@Getter
@NoArgsConstructor
@Entity
@Table(name="Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewIdx;
    @Column(name = "emotionStatus")
    private int emotionStatus;
    @Column(name = "content")
    private String content;
    @Column(name = "created_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    //@ManyToOne
    //@JoinColumn(name = "application_Id")
    //private Application application;
    @ManyToOne
    @JoinColumn(name = "post_Id")
    private Post post;
    //private int userId;

}
