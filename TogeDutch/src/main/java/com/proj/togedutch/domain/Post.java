package com.proj.togedutch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Post")
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int post_id;
    @Column(name="title")
    private String title;
    @Column(name="url")
    private String url;
    @Column(name="delivery_tips")
    private int delivery_tips;
    @Column(name="minimum")
    private int minimum;
    @Column(name="order_time")
    private String order_time;
    @Column(name="num_of_recruits")
    private int num_of_recruits;
    @Column(name="recruited_num")
    private int recruited_num;
    @Column(name="status")
    private String status;
    @Column(name="created_at")
    private Timestamp created_at;
    @Column(name="updated_at")
    private Timestamp updated_at;
    @Column(name="User_user_id")
    private int user_id;
    @Column(name="image")
    private String image;
    @Column(name="latitude")
    private Double latitude;
    @Column(name="longitude")
    private Double longitude;
    
    // Null Value was to a property [DTO] 에러 발생
    // DB에서 not null 지정이 안되어 있는 칼럼의 속성 타입이 primitive type으로 지정
    // int -> integer로 타입 변경
    @Column(name="ChatRoom_chatRoom_id")
    private Integer chatRoom_id;
    @Column(name="category")
    private String category;

    @Builder
    public Post(String title){
        this.title = title;
    }
    @Builder
    public Post(int post_id){
        this.post_id = post_id;
    }
}
