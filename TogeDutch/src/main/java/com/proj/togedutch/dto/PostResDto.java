package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class PostResDto {
    private int post_id;
    private String title;
    private String url;
    private int delivery_tips;
    private int minimum;
    private String order_time;
    private int num_of_recruits;
    private int recruited_num;
    private String status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int user_id;
    private String image;
    private Double latitude;
    private Double longitude;
    private Integer chatRoom_id;
    private String category;

    // Entity to Dto
    @Builder
    public PostResDto(Post post) {
        this.post_id = post.getPostIdx();
        this.title = post.getTitle();
        this.url = post.getUrl();
        this.delivery_tips = post.getDeliveryTips();
        this.minimum = post.getMinimum();
        this.order_time = post.getOrderTime();
        this.num_of_recruits = post.getNumOfRecruits();
        this.recruited_num = post.getRecruitedNum();
        this.status = post.getStatus();
        this.created_at = post.getCreatedAt();
        this.updated_at = post.getUpdatedAt();
        this.user_id = post.getUserIdx();
        this.image = post.getImage();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.chatRoom_id = post.getChatRoomIdx();
        this.category = post.getCategory();
    }
}
