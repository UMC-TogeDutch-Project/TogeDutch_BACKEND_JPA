package com.proj.togedutch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.PseudoColumnUsage;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;
import com.proj.togedutch.domain.Post;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name="Review")
@EntityListeners(AuditingEntityListener.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewIdx;
    @Column(name = "emotionStatus")
    private int emotionStatus;
    @Column(name = "content")
    private String content;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "Application_application_Id")
    private Application application;
    @ManyToOne
    @JoinColumn(name = "Application_Post_post_Id")
    private Post post;
    //private int userId;

    @ManyToOne
    @JoinColumn(name = "Application_Post_User_user_Id")
    private User user;



}
