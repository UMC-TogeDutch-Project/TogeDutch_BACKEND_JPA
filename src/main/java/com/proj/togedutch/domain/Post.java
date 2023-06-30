package com.proj.togedutch.domain;

import com.proj.togedutch.dto.PostReqDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name="Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private int postIdx;
    @Column(name="title")
    private String title;
    @Column(name="url")
    private String url;
    @Column(name="delivery_tips")
    private int deliveryTips;
    @Column(name="minimum")
    private int minimum;
    @Column(name="order_time")
    private String orderTime;
    @Column(name="num_of_recruits")
    private int numOfRecruits;
    @Column(name="recruited_num")
    private int recruitedNum;
    @Column(name="status")
    private String status;
    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp createdAt;
    @Column(name="updated_at")
    private Timestamp updatedAt;
    @Column(name="User_user_id")
    private int userIdx;
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
    private Integer chatRoomIdx;
    @Column(name="category")
    private String category;

    @Builder
    public Post (String title, String url, int delivery_tips, int minimum,
                String order_time, int num_of_recruits, int recruited_num, String status,
                Double latitude, Double longitude, String category, String image, int user_id) {
        this.title = title;
        this.url = url;
        this.deliveryTips = delivery_tips;
        this.minimum = minimum;
        this.orderTime = order_time;
        this.numOfRecruits = num_of_recruits;
        this.recruitedNum = recruited_num;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.image = image;
        this.userIdx = user_id;
    }

    public void insertChatRoom(Integer chatRoomIdx){
        this.chatRoomIdx = chatRoomIdx;
    }

    public void updatePost(PostReqDto post, String fileUrl){
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
        this.title = post.getTitle();
        this.url = post.getUrl();
        this.deliveryTips = post.getDelivery_tips();
        this.minimum = post.getMinimum();
        this.orderTime = post.getOrder_time();
        this.numOfRecruits = post.getNum_of_recruits();
        this.recruitedNum = post.getRecruited_num();
        this.status = post.getStatus();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.category = post.getCategory();
        this.image = fileUrl;
    }
    public void updateStatusPost(){

        this.status = "공고사용불가";
    }

    public void modifyStatusPost(String status){
        this.status=status;
    }
}
