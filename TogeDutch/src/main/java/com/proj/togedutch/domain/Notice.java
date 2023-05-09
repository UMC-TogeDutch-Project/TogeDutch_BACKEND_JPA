package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name="Notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notice_id")
    private int noticeId;
    @Column(name="title") //request
    private String title;
    @Column(name="content") //request
    private String content;
    @Column(name="created_at")
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(name="updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Builder
    public Notice(int noticeId, String title, String content, Timestamp createdAt, Timestamp updatedAt) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void modifyNotice(String content,String title){
        this.content=content;
        this.title=title;
    }

}
