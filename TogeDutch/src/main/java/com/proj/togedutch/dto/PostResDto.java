package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class PostResDto {
    @ApiModelProperty(example = "공고 생성시 부여된 id")
    private int post_id;
    @ApiModelProperty(example = "제목")
    private String title;
    @ApiModelProperty(example = "첨부한 URL")
    private String url;
    @ApiModelProperty(example = "배달팁")
    private int delivery_tips;
    @ApiModelProperty(example = "최소 주문 금액")
    private int minimum;
    @ApiModelProperty(example = "주문 시간")
    private String order_time;
    @ApiModelProperty(example = "모집 인원")
    private int num_of_recruits;
    @ApiModelProperty(example = "모집된 인원")
    private int recruited_num;
    @ApiModelProperty(example = "공고 상태")
    private String status;
    @ApiModelProperty(example = "최초 작성 시간")
    private Timestamp created_at;
    @ApiModelProperty(example = "마지막으로 수정한 시간")
    private Timestamp updated_at;
    @ApiModelProperty(example = "공고 생성자의 id")
    private int user_id;
    @ApiModelProperty(example = "첨부한 이미지 URL")
    private String image;
    @ApiModelProperty(example = "위도")
    private Double latitude;
    @ApiModelProperty(example = "경도")
    private Double longitude;
    @ApiModelProperty(example = "채팅방 생성시 부여된 id")
    private Integer chatRoom_id;
    @ApiModelProperty(example = "카테고리")
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
